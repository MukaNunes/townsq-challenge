package com.townsq.challenge.repositories;


import com.townsq.challenge.domain.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRespository extends JpaRepository<Item, Long> {
    List<Item> findByOrderId(Long order);
}
