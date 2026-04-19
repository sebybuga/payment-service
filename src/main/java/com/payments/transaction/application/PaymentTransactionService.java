package com.payments.transaction.application;


import com.payments.transaction.api.PaymentRequestDTO;
import com.payments.transaction.api.PaymentResponseDTO;
import com.payments.transaction.domain.PaymentStatus;
import com.payments.transaction.domain.PaymentTransaction;
import com.payments.transaction.infrastructure.PaymentTransactionRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentTransactionService {

    private final PaymentTransactionRepository paymentTransactionRepository;

    @Transactional
    public PaymentResponseDTO createPayment(PaymentRequestDTO request) {

        log.info("Creating payment for senderId: {}", request.getSenderId());

        try {
            PaymentTransaction paymentTransaction = PaymentTransaction.builder()
                    .amount(request.getAmount())
                    .currency(request.getCurrency())
                    .senderId(request.getSenderId())
                    .receiverId(request.getReceiverId())
                    .status(PaymentStatus.PENDING)
                    .build();

            PaymentTransaction saved = paymentTransactionRepository.save(paymentTransaction);

            return toResponseDTO(saved);

        } catch (Exception e) {
            throw new PaymentProcessingException("Failed to create payment", e);
        }

    }

    @Transactional(readOnly = true)
    public PaymentResponseDTO getPaymentById(Long id) {
        log.info("Fetching payment with id: {}", id);

        return paymentTransactionRepository.findById(id)
                .map(this ::toResponseDTO)
                .orElseThrow( () ->new PaymentNotFoundException(id));


    }

    @Transactional
    public PaymentResponseDTO updatePaymentStatus(Long id, PaymentStatus status) {
        log.info("Updating payment {} to status {}", id, status);
        PaymentTransaction transaction = paymentTransactionRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));

        transaction.setStatus(status);
        PaymentTransaction saved = paymentTransactionRepository.save(transaction);
        return toResponseDTO(saved);
    }


    private PaymentResponseDTO toResponseDTO(PaymentTransaction transaction) {
        return PaymentResponseDTO.builder()
                .id(transaction.getId())
                .status(transaction.getStatus())
                .createdAt(transaction.getCreatedAt())
                .updatedAt(transaction.getUpdatedAt())
                .build();
    }


}
