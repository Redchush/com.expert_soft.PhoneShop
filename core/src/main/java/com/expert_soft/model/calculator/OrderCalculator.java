package com.expert_soft.model.calculator;


import com.expert_soft.model.order.Cart;
import com.expert_soft.model.order.Order;

public interface OrderCalculator {

    void recalculate(Cart cart);
    void recalculate(Order order);
}
