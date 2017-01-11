package com.expert_soft.service;


import com.expert_soft.model.Order;

import java.util.List;

public interface OrderService {

    Order getOrder(Long key);
    void saveOrder(Order order);
    List<Order> findAll();
}
