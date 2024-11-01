package com.townsq.challenge.api.controllers;

import com.townsq.challenge.api.domain.ItemRequest;
import com.townsq.challenge.api.domain.OrderRequest;
import com.townsq.challenge.api.domain.OrderResponse;
import com.townsq.challenge.domain.enums.OrderStatus;
import com.townsq.challenge.domain.enums.UserRole;
import com.townsq.challenge.domain.exceptions.InvalidTokenUserDataException;
import com.townsq.challenge.domain.exceptions.OrderNotFoundException;
import com.townsq.challenge.domain.models.Item;
import com.townsq.challenge.domain.models.Order;
import com.townsq.challenge.domain.models.User;
import com.townsq.challenge.services.ItemService;
import com.townsq.challenge.services.OrderService;
import com.townsq.challenge.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final String[] allowedRoles = {UserRole.ADMIN.toString(), UserRole.ACCOUNT_MANAGER.toString()};

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @PreAuthorize("hasAuthority('DEFAULT')")
    @PostMapping
    public ResponseEntity<OrderResponse> insert(@RequestBody OrderRequest order, Authentication authentication) {
        User user = userService.getByUsernameOptional(authentication.getName())
                .orElseThrow(InvalidTokenUserDataException::new);

        Order newOrder = new Order();
        newOrder.setUser(user);
        newOrder.setStatus(OrderStatus.AWAITING_PAYMENT);

        List<Item> items = new ArrayList<>();

        for (ItemRequest itemRequest : order.getItems()) {
            Item item = new Item();
            item.setDescription(itemRequest.getDescription());
            item.setQuantity(itemRequest.getQuantity());
            item.setValue(itemRequest.getValue());
            item.setOrder(newOrder);
            items.add(item);
        }
        newOrder.setItems(items);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.save(newOrder));
    }

    @PreAuthorize("hasAuthority('DEFAULT')")
    @GetMapping
    public List<Order> getAll(Authentication authentication) {
        User user = userService.getByUsernameOptional(authentication.getName())
                .orElseThrow(InvalidTokenUserDataException::new);

        if (Arrays.asList(allowedRoles).contains(user.getRole().toString())) {
            return orderService.findAll();
        } else {
            return orderService.getByUser(user);
        }
    }

    @PreAuthorize("hasAuthority('DEFAULT')")
    @GetMapping("/{id}")
    public ResponseEntity<Order> findOne(@PathVariable Long id, Authentication authentication) {
        try {
            User user = userService.getByUsernameOptional(authentication.getName())
                    .orElseThrow(InvalidTokenUserDataException::new);

            if (Arrays.asList(allowedRoles).contains(user.getRole().toString())) {
                return ResponseEntity.ok(orderService.getById(id));
            } else {
                return ResponseEntity.ok(orderService.getByIdAndUser(id, user));
            }

        } catch (OrderNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
