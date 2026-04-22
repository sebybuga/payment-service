package com.payments.transaction.api;

import com.payments.transaction.application.PaymentTransactionService;
import com.payments.transaction.domain.PaymentStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Create a new payment", description = "Creates a payment transaction with PENDING status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Payment processing failed")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentResponseDTO> createPayment(
            @RequestHeader String applicationId,
            @RequestBody @Valid PaymentRequestDTO requestDTO){
        log.info("Request for creating payment for application id: {}", applicationId);
        return ResponseEntity
                .status(HttpStatusCode.valueOf(201))
                .body(service.createPayment(requestDTO));
    }

    @Operation(summary = "Get payment by id", description = "Fetches payment information from database by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "An error has occurred")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentDetailsDTO> getPaymentById(
            @RequestHeader String applicationId,
            @PathVariable Long id){
        return ResponseEntity.ok(service.getPaymentById(id));
    }

    @Operation(summary = "Update payment status", description = "Updates status of an existing payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payment updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Payment update failed")
    })
    @PatchMapping(value = "/{id}/status", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PaymentResponseDTO> updatePaymentStatus(
            @RequestHeader String applicationId,
            @PathVariable long id,
            @RequestBody PaymentStatus status){
        log.info("Request for updating payment with id {} for application id: {}", id, applicationId);
        return ResponseEntity.ok(service.updatePaymentStatus(id, status));
    }
}
