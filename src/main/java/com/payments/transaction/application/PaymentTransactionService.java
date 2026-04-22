package com.payments.transaction.application;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payments.idempotency.IdempotencyRecord;
import com.payments.idempotency.IdempotencyRepository;
import com.payments.idempotency.IdempotencyService;
import com.payments.transaction.api.PaymentDetailsDTO;
import com.payments.transaction.api.PaymentRequestDTO;
import com.payments.transaction.api.PaymentResponseDTO;
import com.payments.transaction.domain.PaymentCreatedEvent;
import com.payments.transaction.domain.PaymentStatus;
import com.payments.transaction.domain.PaymentTransaction;
import com.payments.transaction.infrastructure.PaymentEventProducer;
import com.payments.transaction.infrastructure.PaymentTransactionRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentTransactionService {

    private final PaymentTransactionRepository paymentTransactionRepository;
    private final PaymentEventProducer paymentEventProducer;
    private final ModelMapper modelMapper;
    private final IdempotencyService idempotencyService;
    private final IdempotencyRepository idempotencyRepository;
    private final ObjectMapper objectMapper;

    @CircuitBreaker(name = "payment-db", fallbackMethod = "createPaymentFallback")
    @Retry(name = "payment-db")
    @Transactional
    public PaymentResponseDTO createPayment(String idempotencyKey, PaymentRequestDTO request) throws JsonProcessingException {
        log.info("Creating payment for senderId: {}", request.getSenderId());

        //idempotency check
        if (idempotencyKey != null && idempotencyService.existsByKey(idempotencyKey)) {
            log.info("Duplicate request detected for key: {}", idempotencyKey);
            return idempotencyService.getResponse(idempotencyKey)
                    .map(json -> {
                        try {
                            return objectMapper.readValue(json, PaymentResponseDTO.class);
                        } catch (Exception e) {
                            throw new PaymentProcessingException("Failed to deserialize cached response", e);
                        }
                    })
                    .orElseThrow(() -> new PaymentNotFoundException(-1L));
        }

        PaymentTransaction paymentTransaction = toEntity(request);

        PaymentTransaction saved = paymentTransactionRepository.save(paymentTransaction);

        PaymentCreatedEvent event = PaymentCreatedEvent.builder()
                .paymentId(saved.getId())
                .amount(saved.getAmount())
                .currency(saved.getCurrency())
                .status(saved.getStatus())
                .timestamp(LocalDateTime.now())
                .build();

        paymentEventProducer.sendPaymentCreatedEvent(event);

        PaymentResponseDTO response = toResponseDTO(saved);

        //save idempotency record
        if (idempotencyKey != null){
            try {
                IdempotencyRecord idempotencyRecord = IdempotencyRecord.builder()
                        .idempotencyKey(idempotencyKey)
                        .response(objectMapper.writeValueAsString(response))
                        .createdAt(LocalDateTime.now())
                        .build();
                idempotencyRepository.save(idempotencyRecord);
            } catch (Exception e){
                log.warn("Failed to save idempotency record: {}", e.getMessage());
            }
        }

        return response;


    }

    public PaymentResponseDTO createPaymentFallback(PaymentRequestDTO request, Exception ex) {
        log.error("Circuit breaker triggered for createPayment: {}, sender: {}", ex.getMessage(), request.getSenderId());
        throw new PaymentProcessingException("Payment service temporarily unavailable", ex);
    }

    @CircuitBreaker(name = "payment-db", fallbackMethod = "getPaymentFallback")
    @Transactional(readOnly = true)
    public PaymentDetailsDTO getPaymentById(Long id) {
        log.info("Fetching payment with id: {}", id);

        return paymentTransactionRepository.findById(id)
                .map(this::toDetailsDTO)
                .orElseThrow(() -> new PaymentNotFoundException(id));
    }

    public PaymentDetailsDTO getPaymentFallback(Long id, Exception ex) {
        log.error("Circuit breaker triggered for getPaymentById: {}, id: {}", ex.getMessage(), id);
        throw new PaymentProcessingException("Payment service temporarily unavailable", ex);
    }

    @Transactional
    public PaymentResponseDTO updatePaymentStatus(Long id, PaymentStatus status) {
        log.info("Updating payment {} to status {}", id, status);
        PaymentTransaction transaction = paymentTransactionRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));

        transaction.setStatus(status);
        transaction.setUpdatedAt(LocalDateTime.now());
        PaymentTransaction saved = paymentTransactionRepository.save(transaction);
        return toResponseDTO(saved);
    }


    private PaymentResponseDTO toResponseDTO(PaymentTransaction transaction) {
        return modelMapper.map(transaction, PaymentResponseDTO.class);
    }

    private PaymentDetailsDTO toDetailsDTO(PaymentTransaction transaction) {
        return modelMapper.map(transaction, PaymentDetailsDTO.class);
    }

    private PaymentTransaction toEntity(PaymentRequestDTO request) {
        PaymentTransaction transaction = modelMapper.map(request, PaymentTransaction.class);
        transaction.setStatus(PaymentStatus.PENDING);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setUpdatedAt(LocalDateTime.now());
        return transaction;

    }


}
