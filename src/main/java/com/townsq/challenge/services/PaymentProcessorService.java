package com.townsq.challenge.services;


import com.townsq.challenge.domain.enums.PaymentStatus;

import java.util.Random;

public class PaymentProcessorService {
    private static final Random random = new Random();

    public static PaymentStatus processPayment() {
        PaymentStatus[] status = PaymentStatus.values();
        return status[random.nextInt(status.length)];
    }
}

