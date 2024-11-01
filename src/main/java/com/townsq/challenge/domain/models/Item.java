package com.townsq.challenge.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity(name = "items")
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "orderId")
    private Order order;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private BigDecimal value;
}
