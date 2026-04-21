package com.payments.transaction.domain;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCreatedEvent {

    private Long paymentId;
    private BigDecimal amount;
    private Currency currency;
    private PaymentStatus status;
    private LocalDateTime timestamp;
}