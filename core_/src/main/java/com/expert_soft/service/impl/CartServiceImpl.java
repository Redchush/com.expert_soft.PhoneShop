package com.expert_soft.service.impl;


import com.expert_soft.model.Calculator;
import com.expert_soft.model.Cart;
import com.expert_soft.model.OrderItem;
import com.expert_soft.model.Phone;
import com.expert_soft.model.excluded.CartCurriculum;
import com.expert_soft.persistence.PhoneDao;
import com.expert_soft.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.concurrent.ConcurrentMap;


@Service
public class CartServiceImpl implements CartService {

    private PhoneDao phoneDao;
    private Calculator calculator;

    @Autowired
    public void setPhoneDao(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    @Autowired
    public void setCalculator(Calculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public boolean addToCart(Cart cart, Phone phone, Integer quantity) {
        OrderItem possiblySameItem = getItem(phone.getKey(), cart);
        if (possiblySameItem == null){
            putItem(createOrderItem(phone, quantity), cart);
            return true;
        } else {
            Integer totalQuantity = possiblySameItem.getQuantity() + quantity;
            possiblySameItem.setQuantity(totalQuantity);
            putItem(phone.getKey(), possiblySameItem, cart);
            return false;
        }
    }

    @Override
    public Phone addToCart(Cart cart, Long phoneId, Integer quantity) {
        Phone phone = phoneDao.getPhone(phoneId);
        addToCart(cart, phone, quantity);
        return phone;
    }

    public void addToCart(CartCurriculum curriculum, Long phoneId, Integer quantity){
    }

    @Override
    public OrderItem deleteFromCart(Cart cart, Long phoneId) {
        return removeByPhoneKey(phoneId, cart);
    }

    @Override
    public Cart deleteFromCart(Cart cart, Long[] phoneIdArray){
        ConcurrentMap<Long, OrderItem> itemsMap = cart.getItemsMap();
        for (Long phoneId : phoneIdArray) {
            itemsMap.remove(phoneId);
        }
        return cart;
    }

    @Override
    public OrderItem changeQuantity(Cart cart, Long phoneId, Integer newQuantity) {
        OrderItem item = getItem(phoneId, cart);
        if (newQuantity.equals(item.getQuantity())){
            return removeByPhoneKey(phoneId, cart);
        } else {
            item.setQuantity(newQuantity);
            putItem(item, cart);
            return item;
        }
    }

    private OrderItem createOrderItem(Phone phone, Integer quantity){
        OrderItem result = new OrderItem();
        result.setPhone(phone);
        result.setQuantity(quantity);
        return result;
    }

    @Override
    public BigDecimal calculateAndSetSubtotal(Cart cart){
        BigDecimal result = cart.recalculateSubtotal(calculator);
        cart.setSubtotal(result);
        return result;
    }

    @Override
    public CartCurriculum buildCurriculum(Cart cart){
        CartCurriculum result = new CartCurriculum();
        result.setCartSize(cart.totalItemsCount());
        result.setCartSubtotal(cart.recalculateSubtotal(calculator));
        return result;
    }

    private void putItem(OrderItem item, Cart cart){
        cart.getItemsMap().put(item.getUniqueKey(), item);
    }

    private void putItem(Long phoneId, OrderItem item, Cart cart){
        cart.getItemsMap().put(phoneId, item);
    }

    private void removeItem(OrderItem item, Cart cart){
        cart.getItemsMap().remove(item.getUniqueKey());
    }

    private OrderItem removeByPhoneKey(Long phoneKey, Cart cart){
        return cart.getItemsMap().remove(phoneKey);
    }

    private boolean contains(OrderItem item, Cart cart){
        return cart.getItemsMap().containsKey(item.getUniqueKey());
    }

    private OrderItem getItem(OrderItem item, Cart cart){
        return getItem(item.getPhone().getKey(), cart);
    }

    private OrderItem getItem(Long phoneKey, Cart cart){
        return cart.getItemsMap().get(phoneKey);
    }

    private Collection<OrderItem> getAllItems(Cart cart){
        return cart.getItemsMap().values();
    }

}
