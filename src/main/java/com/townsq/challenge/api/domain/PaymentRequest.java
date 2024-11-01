package com.townsq.challenge.api.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {
    @NonNull
    private Long order;

    @NonNull
    private String paymentInfo;
}
