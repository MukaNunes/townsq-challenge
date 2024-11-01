package com.townsq.challenge.repositories;


import com.townsq.challenge.domain.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRespository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long user);

    Optional<Order> findByIdAndUserId(Long id, Long userId);
}
