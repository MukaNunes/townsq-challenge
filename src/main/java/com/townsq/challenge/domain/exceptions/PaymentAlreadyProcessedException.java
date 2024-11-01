package com.townsq.challenge.domain.exceptions;

public class PaymentAlreadyProcessedException extends RuntimeException {
    public PaymentAlreadyProcessedException() {
        super("Payment already processed");
    }
}
