package com.expert_soft.service.impl;


import com.expert_soft.model.*;
import com.expert_soft.model.order.Cart;
import com.expert_soft.model.order.Order;
import com.expert_soft.persistence.OrderDao;
import com.expert_soft.persistence.PhoneDao;
import com.expert_soft.service.DeliveryService;
import com.expert_soft.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;
    private PhoneDao phoneDao;

    private DeliveryService deliveryService;
    private Calculator calculator;

    @Autowired
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Autowired
    public void setPhoneDao(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    @Autowired
    public void setCalculator(Calculator calculator) {
        this.calculator = calculator;
    }

    @Autowired
    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @Override
    public Order getOrder(Long key) {
        return orderDao.getOrder(key);
    }

    @Override
    public Long saveOrder(Order order) {
        return orderDao.saveOrder(order);
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    public Order buildOrder(Cart cart, boolean deep) {
        Order order = initOrder(cart, deep);
        order.calculateFull(calculator);
        return order;
    }

    @Override
    public Order buildOrder(Cart cart, UserInfo info, boolean deep){
        Order order = buildOrder(cart, deep);
        order.setUserInfo(info);
        return order;
    }

    private Order initOrder(Cart cart, boolean deep){
        Order order = new Order();
        BigDecimal deliveryPrice = deliveryService.getDeliveryPrice();
        order.setDeliveryPrice(deliveryPrice);
        Collection<OrderItem> allItems = cart.getAllItems();
        order.setOrderItems(new ArrayList<>(allItems));
        return order;
    }
}
