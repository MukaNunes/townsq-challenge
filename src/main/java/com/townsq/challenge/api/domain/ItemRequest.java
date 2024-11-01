package com.townsq.challenge.api.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemRequest {
    private String description;
    private Long quantity;
    private BigDecimal value;
}