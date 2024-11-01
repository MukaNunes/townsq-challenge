package com.townsq.challenge.api.domain;

import com.townsq.challenge.domain.enums.OrderStatus;
import com.townsq.challenge.domain.models.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class OrderResponse {
    private Long id;
    private BigDecimal totalValue;
    private OrderStatus status;
    private List<Item> items;
}