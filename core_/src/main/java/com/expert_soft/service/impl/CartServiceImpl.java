package com.expert_soft.service.impl;


import com.expert_soft.model.Calculator;
import com.expert_soft.model.Cart;
import com.expert_soft.model.OrderItem;
import com.expert_soft.model.Phone;
import com.expert_soft.persistence.PhoneDao;
import com.expert_soft.service.CartService;
import com.expert_soft.validator.group.G_Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class CartServiceImpl implements CartService {

    private PhoneDao phoneDao;
    private Calculator calculator;
    private Validator validator;

    @Override @Autowired
    public void setPhoneDao(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }
    @Override @Autowired
    public void setCalculator(Calculator calculator) {
        this.calculator = calculator;
    }

    @Autowired
    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    @Override
    public OrderItem addToCart(Cart cart, Phone phone, Integer quantity) {
        OrderItem possiblySameItem = cart.getItem(phone.getKey());
        Integer newQuantity = quantity;
        if (possiblySameItem != null){
            newQuantity = quantity + possiblySameItem.getQuantity();
        }
        OrderItem result = new OrderItem(phone, newQuantity);
        Set<ConstraintViolation<OrderItem>> validate = validator.validate(result, G_Cart.Item.class);
        if (!validate.isEmpty()){
            throw new ConstraintViolationException("Invalid order updatedItem", validate);
        }
        cart.putItem(result);
        return result;
    }

    @Override
    public OrderItem deepAddToCart(Cart cart, Long phoneId, Integer quantity){
        Phone phone = phoneDao.getPhone(phoneId);
        return addToCart(cart, phone, quantity);
    }

    @Override
    public OrderItem deleteFromCart(Cart cart, Long phoneId) {
        return cart.removeByPhoneKey(phoneId);
    }

    @Override
    public OrderItem updatePhoneQuantity(Cart cart, OrderItem updatedItem) {
        Long phoneKey =  updatedItem.getPhone().getKey();
        OrderItem item = cart.getItem(phoneKey);
        item.setQuantity(updatedItem.getQuantity());
        cart.putItem(phoneKey, item);
        return updatedItem;
    }

    @Override
    public BigDecimal calculateAndSetSubtotal(Cart cart){
        BigDecimal result = cart.recalculateSubtotal(calculator);
        cart.setSubtotal(result);
        return result;
    }

    @Override
    public Cart calculateAndSetSize(Cart cart){
        cart.setCartSize(cart.totalItemsCount());
        return cart;
    }

    @Override
    public void deeplyCheckCart(Cart cart) {
        List<Long> phonesInCart = cart.getAllItems().stream().map(OrderItem::getKey)
                                      .collect(Collectors.toList());

        List<Phone> phones = phoneDao.getPhones(phonesInCart);
        boolean needRecalculate = false;
        for (Phone phone: phones){
            OrderItem orderItem = cart.getItem(phone.getKey());
            if (!phone.equals(orderItem.getPhone())){
                needRecalculate = true;
            }
        }
        if (needRecalculate){
            calculateAndSetSubtotal(cart);
        }

    }

    @Override
    public OrderItem createNewOrderItem(Phone phone, Integer quantity)
            throws ConstraintViolationException {
        return new OrderItem(phone, quantity);
    }

}
