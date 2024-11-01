package com.townsq.challenge.api.controllers;


import com.townsq.challenge.api.domain.PaymentRequest;
import com.townsq.challenge.api.domain.PaymentResponse;
import com.townsq.challenge.domain.enums.OrderStatus;
import com.townsq.challenge.domain.enums.PaymentStatus;
import com.townsq.challenge.domain.exceptions.OrderNotFoundException;
import com.townsq.challenge.domain.exceptions.PaymentAlreadyProcessedException;
import com.townsq.challenge.domain.models.Order;
import com.townsq.challenge.domain.models.Payment;
import com.townsq.challenge.services.OrderService;
import com.townsq.challenge.services.PaymentProcessorService;
import com.townsq.challenge.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderService orderService;

    private PaymentProcessorService paymentProcessorService;

    @PreAuthorize("hasAuthority('ACCOUNT_MANAGER')")
    @PostMapping
    public ResponseEntity<PaymentResponse> insert(@RequestBody PaymentRequest payment) {
        Order order = orderService.getById(payment.getOrder());

        if (order.getStatus() == OrderStatus.COMPLETED) {
            throw new PaymentAlreadyProcessedException();
        }

        Payment newPayment = new Payment();
        PaymentStatus paymentStatus = PaymentProcessorService.processPayment();

        if (paymentStatus != PaymentStatus.PENDING) {
            order.setStatus(OrderStatus.COMPLETED);
            orderService.save(order);
        }

        newPayment.setOrder(orderService.getById(payment.getOrder()));
        newPayment.setStatus(paymentStatus);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(paymentService.save(newPayment));
    }

    @PreAuthorize("hasAuthority('DEFAULT')")
    @GetMapping("/{id}")
    public ResponseEntity<Payment> findOne(@PathVariable Long id) {
        try {
            var response = paymentService.getById(id);
            return ResponseEntity.ok(response);
        } catch (OrderNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
