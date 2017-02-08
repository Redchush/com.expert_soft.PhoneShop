package com.expert_soft.service.impl;


import com.expert_soft.model.OrderItem;
import com.expert_soft.model.Phone;
import com.expert_soft.model.calculator.OrderCalculator;
import com.expert_soft.model.order.Cart;
import com.expert_soft.model.result.ValidationResult;
import com.expert_soft.persistence.PhoneDao;
import com.expert_soft.service.CartService;
import com.expert_soft.validator.group.G_Cart;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Service("cartService")
public class CartServiceImpl implements CartService {

    private PhoneDao phoneDao;
    private OrderCalculator calculator;
    private Validator validator;

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public void setPhoneDao(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    public void setCalculator(OrderCalculator calculator) {this.calculator = calculator;}

    @Override
    public Cart getCart() {
        return new Cart();
    }

    @Override
    public boolean isCartEmpty(Cart cart) {
        return cart == null || cart.getOrderItems().isEmpty();
    }

    @Override
    public int getCartSize(Cart cart) {
        return cart.getOrderItems().size();
    }

    @Override
    public Phone getPhone(Cart cart, Long phoneId) {
        return cart.getItem(phoneId).getPhone();
    }

    @Override
    public ValidationResult<OrderItem> addToCart(Cart cart, Phone phone, Integer quantity) {
        OrderItem prevItem = cart.getItem(phone.getKey());
        Integer newQuantity = quantity;
        if (prevItem != null){
            newQuantity = quantity + prevItem.getQuantity();
        }
        return addToCart(cart, new OrderItem(phone, newQuantity), prevItem);
    }

    @Override
    public ValidationResult<OrderItem> addToCart(Cart cart, Long phoneKey, Integer quantity) {
        OrderItem prevItem = cart.getItem(phoneKey);
        Phone phone;
        Integer newQuantity = quantity;
        if (prevItem != null){
            newQuantity = quantity + prevItem.getQuantity();
            phone = prevItem.getPhone();
        } else {
            phone = phoneDao.getPhone(phoneKey);
        }
        return addToCart(cart, new OrderItem(phone, newQuantity), prevItem);
    }

    @Override
    public OrderItem deleteFromCart(Cart cart, Long phoneId) {
        OrderItem item = cart.removeItem(phoneId);
        calculator.recalculate(cart);
        return item;
    }

    @Override
    public Cart deleteFromCart(Cart cart, Long[] phoneKeys) {
        if (phoneKeys != null) {
            for (Long phoneId : phoneKeys) {
                deleteFromCart(cart, phoneId);
            }
        }
        calculator.recalculate(cart);
        return cart;
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

    private ValidationResult<OrderItem> addToCart(Cart cart, OrderItem item, OrderItem prevItem){
        Set<ConstraintViolation<OrderItem>> violations
                = validator.validate(item, G_Cart.Item.class);
        if (!violations.isEmpty()){
            return new ValidationResult<>(violations, prevItem);
        }
        cart.addItem(item);
        calculator.recalculate(cart);
        return ValidationResult.SUCCESS_VALIDATION_RESULT;
    }


}
