package com.expert_soft.service;


import com.expert_soft.model.Cart;
import com.expert_soft.model.Order;
import com.expert_soft.model.UserInfo;

import java.util.List;

public interface OrderService {

    Order getOrder(Long key);
    Long saveOrder(Order order);
    List<Order> findAll();

    Order buildOrder(Cart cart);

    Order buildOrder(Cart cart, UserInfo info);
}
