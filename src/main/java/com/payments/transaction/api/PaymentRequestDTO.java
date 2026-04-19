package com.payments.transaction.api;

import com.payments.transaction.domain.Currency;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Getter
@Setter
@Jacksonized
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDTO {
    @NotNull
    private BigDecimal amount;

    @NotNull
    private Currency currency;

    @NotNull
    @Positive
    private Integer senderId;

    @NotNull
    @Positive
    private Integer receiverId;
}
