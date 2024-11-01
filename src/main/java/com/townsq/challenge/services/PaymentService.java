package com.townsq.challenge.services;

import com.townsq.challenge.api.domain.PaymentResponse;
import com.townsq.challenge.domain.exceptions.PaymentNotFoundException;
import com.townsq.challenge.domain.models.Payment;
import com.townsq.challenge.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    private PaymentResponse buildResponse(Payment payment) {
        return PaymentResponse.builder().withId(payment.getId()).withStatus(payment.getStatus()).build();
    }

    public Payment getById(final Long id) {
        return paymentRepository.findById(id).orElseThrow(PaymentNotFoundException::new);
    }

    public PaymentResponse save(Payment payment) {
        var response = paymentRepository.save(payment);
        return this.buildResponse(response);
    }

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

}
