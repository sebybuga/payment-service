package com.payments.transaction.api;

import com.payments.transaction.application.PaymentTransactionService;
import com.payments.transaction.domain.PaymentStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/payments")
@Slf4j
@RequiredArgsConstructor
public class PaymentTransactionController {

    private final PaymentTransactionService service;

    @PostMapping(value = "/create-payment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentResponseDTO> createPayment(
            @RequestHeader String applicationId,
            @RequestBody @Valid PaymentRequestDTO requestDTO){
        log.info("Request for creating payment for application id: {}", applicationId);
        return ResponseEntity
                .status(HttpStatusCode.valueOf(201))
                .body(service.createPayment(requestDTO));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentResponseDTO> getPaymentById(
            @RequestHeader String applicationId,
            @PathVariable Long id){
        return ResponseEntity.ok(service.getPaymentById(id));
    }

    @PatchMapping(value = "/{id}/status", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PaymentResponseDTO> updatePaymentStatus(
            @RequestHeader String applicationId,
            @PathVariable long id,
            @RequestParam @Valid PaymentStatus status){
        log.info("Request for updating payment with id {} for application id: {}", id, applicationId);
        return ResponseEntity.ok(service.updatePaymentStatus(id, status));
    }




}
