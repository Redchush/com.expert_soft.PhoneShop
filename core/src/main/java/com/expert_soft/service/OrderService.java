package com.expert_soft.service;


import com.expert_soft.model.order.Cart;
import com.expert_soft.model.order.Order;
import com.expert_soft.model.order.OrderStatus;
import com.expert_soft.model.order.UserInfo;

import javax.validation.ConstraintViolationException;
import java.util.List;

public interface OrderService {

    Order getOrder(Long key);

    Long saveOrder(Order order);

    List<Order> findAll();


    /**
     * It is client responsible to validate cart
     * @param cart - on which order must be build
     * @param deep - whether data need to be checked in persistence
     * @return result Order
     */
    Order buildOrder(Cart cart, UserInfo info, boolean deep)
                                throws ConstraintViolationException;

    void changeStatus(Long key, OrderStatus status);

}
