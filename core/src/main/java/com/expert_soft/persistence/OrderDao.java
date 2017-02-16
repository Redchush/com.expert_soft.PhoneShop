package com.expert_soft.persistence;


import com.expert_soft.model.order.Order;
import com.expert_soft.model.order.OrderStatus;

import java.util.List;

public interface OrderDao {

    /**
     * return full-stuffed Order with one or more OrderItems
     */
    Order getOrder(Long key);

    void changeStatus(Long orderId, OrderStatus status);

    Long saveOrder(Order order);

    List<Order> findAll();

}
