package com.townsq.challenge.repositories;

import com.townsq.challenge.domain.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
