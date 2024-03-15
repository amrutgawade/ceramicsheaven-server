package com.ceramicsheaven.services;

import com.ceramicsheaven.repositories.OrderItemRepository;
import com.ceramicsheaven.model.OrderItem;

public class OrderItemServiceImplementation implements OrderItemService{

    private OrderItemRepository orderItemRepository;
    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
}
