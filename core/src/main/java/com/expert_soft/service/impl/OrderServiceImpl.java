package com.expert_soft.service.impl;


import com.expert_soft.model.OrderItem;
import com.expert_soft.model.Phone;
import com.expert_soft.model.UserInfo;
import com.expert_soft.model.calculator.OrderCalculator;
import com.expert_soft.model.order.Cart;
import com.expert_soft.model.order.Order;
import com.expert_soft.persistence.OrderDao;
import com.expert_soft.persistence.PhoneDao;
import com.expert_soft.service.DeliveryService;
import com.expert_soft.service.OrderService;
import com.expert_soft.validator.group.G_Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;
    private PhoneDao phoneDao;
    private DeliveryService deliveryService;
    private OrderCalculator calculator;
    private Validator validator;

    @Autowired
    public void setValidator(Validator validator) {
        this.validator = validator;
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
    public void setCalculator(OrderCalculator calculator) {this.calculator = calculator;}

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

    @Override
    public OrderItem addToCart(Cart cart, Phone phone, Integer quantity) {
        OrderItem possiblySameItem = cart.getItem(phone.getKey());
        Integer newQuantity = quantity;
        if (possiblySameItem != null){
            newQuantity = quantity + possiblySameItem.getQuantity();
        }
        OrderItem result = new OrderItem(phone, newQuantity);
        Set<ConstraintViolation<OrderItem>> validate
                = validator.validate(result, G_Cart.Item.class);
        if (!validate.isEmpty()){
            throw new ConstraintViolationException("Invalid order updatedItem", validate);
        }
        cart.addItem(result);
        calculator.recalculate(cart);
        return result;
    }

    @Override
    public OrderItem deleteFromCart(Cart cart, Long phoneId) {
        OrderItem item = cart.removeByPhoneKey(phoneId);
        calculator.recalculate(cart);
        return item;
    }

    @Override
    public OrderItem updatePhoneQuantity(Cart cart, OrderItem updatedItem) {
        Long phoneKey =  updatedItem.getPhone().getKey();
        OrderItem item = cart.getItem(phoneKey);
        item.setQuantity(updatedItem.getQuantity());
        cart.addItem(item);
        calculator.recalculate(cart);
        return updatedItem;
    }
}
