package com.payments.transaction.domain;

public enum PaymentStatus {

    PENDING("Payment is PENDING"),
    COMPLETED("Payment is COMPLETED"),
    FAILED("Payment FAILED");
    private String description;


    PaymentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
