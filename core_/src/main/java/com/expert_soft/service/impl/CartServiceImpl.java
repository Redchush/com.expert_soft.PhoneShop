package com.expert_soft.service.impl;


import com.expert_soft.model.Calculator;
import com.expert_soft.model.Cart;
import com.expert_soft.model.OrderItem;
import com.expert_soft.model.Phone;
import com.expert_soft.persistence.PhoneDao;
import com.expert_soft.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import javax.validation.executable.ExecutableType;
import javax.validation.executable.ValidateOnExecution;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Service
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
public class CartServiceImpl implements CartService {

    private PhoneDao phoneDao;
    private Calculator calculator;

    @Override @Autowired
    public void setPhoneDao(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }
    @Override @Autowired
    public void setCalculator(Calculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public boolean addToCart(Cart cart, OrderItem item) {
        OrderItem possiblySameItem = cart.getItem(item.getPhone().getKey());
        if (possiblySameItem == null){
            cart.putItem(item);
            return true;
        } else {
            Integer newQuantity = possiblySameItem.getQuantity() + item.getQuantity();
            item.setQuantity(newQuantity);
            cart.putItem(item);
            return false;
        }
    }

    @Override
    public Phone deepAddToCart(Cart cart, Long phoneId, Integer quantity){
        Phone phone = phoneDao.getPhone(phoneId);
        OrderItem newOrderItem = null;
        newOrderItem = createNewOrderItem(phone, quantity);
        addToCart(cart, newOrderItem);
        return phone;
    }
    @Override
    public OrderItem deleteFromCart(Cart cart, Long phoneId) {
        return cart.removeByPhoneKey(phoneId);
    }
    @Override
    public Cart deleteFromCart(Cart cart, Long[] phoneIdArray){
        for (Long phoneId : phoneIdArray) {
            cart.removeByPhoneKey(phoneId);
        }
        return cart;
    }

    @Override
    public OrderItem changeQuantity(Cart cart, Long phoneId, Integer newQuantity) {
        OrderItem item = cart.getItem(phoneId);
        OrderItem newItem = new OrderItem(item.getPhone(), newQuantity);
        cart.putItem(phoneId, newItem);
        return item;
    }

    @Override
    public void changeQuantity(Cart cart, List<OrderItem> changes) {
        for (OrderItem item :changes){
            changeQuantity(cart, item.getPhone().getKey(), item.getQuantity());
        }
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



//    private OrderItem getItem(OrderItem item, Cart cart){
//        return getItem(item.getPhone().getKey(), cart);
//    }

}
