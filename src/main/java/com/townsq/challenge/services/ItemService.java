package com.townsq.challenge.services;

import com.townsq.challenge.domain.models.Item;
import com.townsq.challenge.domain.models.Order;
import com.townsq.challenge.repositories.ItemRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    @Autowired
    private ItemRespository itemRespository;

    public List<Item> getByOrder(Order order) {
        return itemRespository.findByOrderId(order.getId());
    }


    public Item save(Item item) {
        return itemRespository.save(item);
    }

    public List<Item> findAll() {
        return itemRespository.findAll();
    }

}
