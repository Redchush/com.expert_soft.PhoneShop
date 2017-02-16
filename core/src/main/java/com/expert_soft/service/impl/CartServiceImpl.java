package com.expert_soft.service.impl;


import com.expert_soft.model.Phone;
import com.expert_soft.model.calculator.OrderCalculator;
import com.expert_soft.model.order.Cart;
import com.expert_soft.model.order.OrderItem;
import com.expert_soft.model.result.ValidationResult;
import com.expert_soft.persistence.PhoneDao;
import com.expert_soft.service.CartService;
import com.expert_soft.validator.group.G_Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

/**
 * Need Cart to be declared in context
 */
@Service("cartService")
public class CartServiceImpl implements CartService {

    private PhoneDao phoneDao;
    private OrderCalculator calculator;
    private Validator validator;
    private Cart cart;

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public boolean isCartEmpty() {
        return cart == null || cart.getOrderItems().isEmpty();
    }

    @Override
    public int getCartSize() {
        return cart.getOrderItems().size();
    }

    @Override
    public Phone getPhone(Long phoneId) {
        return cart.getItem(phoneId).getPhone();
    }

    @Override
    public ValidationResult<OrderItem> addToCart(Phone phone, Integer quantity) {
        OrderItem prevItem = this.cart.getItem(phone.getKey());
        Integer newQuantity = quantity;
        if (prevItem != null){
            newQuantity = quantity + prevItem.getQuantity();
        }
        return addToCart(new OrderItem(phone, newQuantity), prevItem);
    }

    @Override
    public ValidationResult<OrderItem> addToCart(Long phoneKey, Integer quantity) {
        OrderItem prevItem = cart.getItem(phoneKey);
        Phone phone;
        Integer newQuantity = quantity;
        if (prevItem != null){
            newQuantity = quantity + prevItem.getQuantity();
            phone = prevItem.getPhone();
        } else {
            phone = phoneDao.getPhone(phoneKey);
        }
        return addToCart(new OrderItem(phone, newQuantity), prevItem);
    }

    @Override
    public OrderItem deleteFromCart(Long phoneKey) {
        OrderItem item = cart.removeItem(phoneKey);
        calculator.recalculate(cart);
        return item;
    }

    @Override
    public Cart deleteFromCart(Long[] phoneKeys) {
        if (phoneKeys != null) {
            for (Long phoneId : phoneKeys) {
                deleteFromCart(phoneId);
            }
        }
        calculator.recalculate(cart);
        return cart;
    }

    @Override
    public OrderItem updatePhoneQuantity(Long phoneKey, Integer newQuantity) {
        OrderItem item = cart.getItem(phoneKey);
        item.setQuantity(newQuantity);
        cart.addItem(item);
        calculator.recalculate(cart);
        return item;
    }

    private ValidationResult<OrderItem> addToCart(OrderItem item, OrderItem prevItem){
        Set<ConstraintViolation<OrderItem>> violations
                = validator.validate(item, G_Cart.Item.class);
        if (!violations.isEmpty()){
            return new ValidationResult<>(violations, prevItem);
        }
        cart.addItem(item);
        calculator.recalculate(cart);
        return ValidationResult.SUCCESS_VALIDATION_RESULT;
    }

    @Autowired
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Autowired
    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    @Autowired
    public void setPhoneDao(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    @Autowired
    public void setCalculator(OrderCalculator calculator) {this.calculator = calculator;}


}
