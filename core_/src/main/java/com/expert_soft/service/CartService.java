package com.expert_soft.service;


import com.expert_soft.model.Cart;
import com.expert_soft.model.OrderItem;
import com.expert_soft.model.Phone;
import com.expert_soft.model.excluded.CartCurriculum;

import java.math.BigDecimal;

public interface CartService {

    /**
     * @param cart - current cart
     * @param phone - phone to be saved
     * @param quantity - quantity of Phones
     * @return whether cart contains same phone
     */
    boolean addToCart(Cart cart, Phone phone, Integer quantity);
    Phone addToCart(Cart cart, Long phoneId, Integer quantity);

    OrderItem deleteFromCart(Cart cart, Long phoneId);

    Cart deleteFromCart(Cart cart, Long[] phoneIdArray);

    OrderItem changeQuantity(Cart cart, Long phoneId, Integer newQuantity);

    BigDecimal calculateAndSetSubtotal(Cart cart);

    CartCurriculum buildCurriculum(Cart cart);
}
