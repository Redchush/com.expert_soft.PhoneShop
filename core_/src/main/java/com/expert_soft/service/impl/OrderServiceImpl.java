package com.expert_soft.service.impl;


import com.expert_soft.config.AppConfigProperties;
import com.expert_soft.model.*;
import com.expert_soft.persistence.OrderDao;
import com.expert_soft.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderDao dao;
    private AppConfigProperties properties;
    private Calculator calculator;

    @Autowired
    public void setDao(OrderDao dao) {
        this.dao = dao;
    }

    @Autowired
    public void setProperties(AppConfigProperties properties) {
        this.properties = properties;
    }

    @Autowired
    public void setCalculator(Calculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public Order getOrder(Long key) {
        return dao.getOrder(key);
    }

    @Override
    public Long saveOrder(Order order) {
       return dao.saveOrder(order);
    }

    @Override
    public List<Order> findAll() {
        return dao.findAll();
    }

    @Override
    public Order buildOrder(Cart cart) {
        Order order = initOrder(cart);
        order.calculateFull(calculator);
        return order;
    }

    @Override
    public Order buildOrder(Cart cart, UserInfo info){
        Order order = buildOrder(cart);
        order.setUserInfo(info);
        return order;
    }


    private Order initOrder(Cart cart){
        Order order = new Order();
        String property = properties.getProperty("delivery.price");
        order.setDeliveryPrice(new BigDecimal(property));
        Collection<OrderItem> allItems = cart.getItemsMap().values();
        order.setOrderItems(new HashSet<>(allItems));
        return order;
    }
}
