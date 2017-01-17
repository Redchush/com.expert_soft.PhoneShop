package com.expert_soft.service.impl;


import com.expert_soft.model.Cart;
import com.expert_soft.model.Order;
import com.expert_soft.model.OrderItem;
import com.expert_soft.model.Phone;
import com.expert_soft.persistence.PhoneDao;
import com.expert_soft.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;


@Service
public class CartServiceImpl implements CartService {

    private PhoneDao phoneDao;

    @Autowired
    public void setPhoneDao(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    @Override
    public boolean addToCart(Cart cart, Phone phone, Long quantity) {
        OrderItem currentItem = createOrderItem(phone, quantity);
        OrderItem possiblySameItem = cart.getItem(currentItem);

        if (possiblySameItem == null){
            Long totalQuantity = possiblySameItem.getQuantity() + quantity;
            possiblySameItem.setQuantity(totalQuantity);
            return false;
        } else {
            cart.addItem(currentItem);
            return true;
        }
    }

    @Override
    public Phone addToCart(Cart cart, Long phoneId, Long quantity) {
        Phone phone = phoneDao.getPhone(phoneId);
        addToCart(cart, phone, quantity);
        return phone;
    }

    public OrderItem deleteFromCart(Cart cart, Phone phone, Long quantity) {
        OrderItem item = createOrderItem(phone, quantity);
        cart.removeItem(item);
        return item;
    }

    @Override
    public OrderItem deleteFromCart(Cart cart, Long phoneId, Long quantity) {
        Phone phone = phoneDao.getPhone(phoneId);
        return deleteFromCart(cart, phone, quantity);
    }

    private OrderItem createOrderItem(Phone phone, Long quantity){
        OrderItem result = new OrderItem();
        result.setPhone(phone);
        result.setQuantity(quantity);
        return result;
    }

    public BigDecimal calculateAndSetSubtotal(Cart cart){
        BigDecimal result = calculateSubtotal(cart.getAllItems());
        cart.setSubtotal(result);
        return result;
    }

    public BigDecimal calculateAndSetSubtotal(Order order){
        BigDecimal result = calculateSubtotal(order.getOrderItems());
        order.setSubtotal(result);
        return result;
    }

    private BigDecimal calculateSubtotal(Collection<OrderItem> items){
        BigDecimal result = new BigDecimal(0);
        for (OrderItem item : items){
            BigDecimal multiply = item.getPhone().getPrice()
                                      .multiply(new BigDecimal(item.getQuantity()));
            result.add(multiply);
        }
        return result;
    }
}
