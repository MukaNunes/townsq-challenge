package com.townsq.challenge.services;

import com.townsq.challenge.api.domain.OrderResponse;
import com.townsq.challenge.domain.exceptions.OrderNotFoundException;
import com.townsq.challenge.domain.models.Order;
import com.townsq.challenge.domain.models.User;
import com.townsq.challenge.repositories.OrderRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRespository orderRespository;

    private OrderResponse buildResponse(Order order) {
        return OrderResponse.builder()
                .withId(order.getId())
                .withStatus(order.getStatus())
                .withTotalValue(order.getTotalValue())
                .withItems(order.getItems())
                .build();
    }

    public Order getByIdAndUser(Long id, User user) {
        return orderRespository.findByIdAndUserId(id, user.getId()).orElseThrow(OrderNotFoundException::new);
    }

    public Order getById(Long id) {
        return orderRespository.findById(id).orElseThrow(OrderNotFoundException::new);
    }

    public OrderResponse getOrderResponseById(Long id) {
        return buildResponse(getById(id));
    }

    public List<Order> getByUser(User user) {
        return orderRespository.findByUserId(user.getId());
    }


    public OrderResponse save(Order order) {
        var response = orderRespository.save(order);
        return this.buildResponse(response);
    }

    public List<Order> findAll() {
        return orderRespository.findAll();
    }

}
