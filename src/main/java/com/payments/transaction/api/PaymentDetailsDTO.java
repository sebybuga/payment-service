package com.payments.transaction.api;

import com.payments.transaction.domain.Currency;
import com.payments.transaction.domain.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetailsDTO {

    private Long id;
    private BigDecimal amount;
    private Currency currency;
    private PaymentStatus status;
    private Long senderId;
    private Long receiverId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}