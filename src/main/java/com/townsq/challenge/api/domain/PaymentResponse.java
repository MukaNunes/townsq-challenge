package com.townsq.challenge.api.domain;

import com.townsq.challenge.domain.enums.PaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class PaymentResponse {
    private Long id;
    private PaymentStatus status;
}