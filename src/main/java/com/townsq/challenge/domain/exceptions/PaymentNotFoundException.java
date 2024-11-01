package com.townsq.challenge.domain.exceptions;

public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException() {
        super("Payment not found");
    }
}
