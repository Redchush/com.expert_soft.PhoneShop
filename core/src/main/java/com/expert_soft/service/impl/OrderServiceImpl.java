package com.expert_soft.service.impl;


import com.expert_soft.model.Phone;
import com.expert_soft.model.calculator.OrderCalculator;
import com.expert_soft.model.order.*;
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

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;
    private PhoneDao phoneDao;
    private DeliveryService deliveryService;
    private OrderCalculator calculator;

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
    public void changeStatus(Long key, OrderStatus status) {
        orderDao.changeStatus(key, status);
    }

    @Override
    public Order buildOrder(Cart cart, UserInfo info, boolean deep){
        Order order = initOrder(cart, deep);
        order.setUserInfo(info);
        calculator.recalculate(order);
        return order;
    }

    private Order initOrder(Cart cart, boolean deep){
        Order order = new Order();
        BigDecimal deliveryPrice = deliveryService.getDeliveryPrice();
        order.setDeliveryPrice(deliveryPrice);
        if (deep){
            Collection<Long> phoneKeys = cart.getPhoneKeys();
            List<Phone> phones = phoneDao.getPhones(phoneKeys);
            for (Phone phone: phones){
                OrderItem orderItem = cart.getItem(phone.getKey());
                orderItem.setPhone(phone);
            }
        }
        Collection<OrderItem> allItems = cart.getOrderItems();
        order.setOrderItems(new ArrayList<>(allItems));
        return order;
    }

    @Autowired
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Autowired
    public void setPhoneDao(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    @Autowired
    public void setCalculator(OrderCalculator calculator) {
        this.calculator = calculator;
    }

    @Autowired
    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }




}
