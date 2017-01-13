package com.expert_soft.service.impl;


import com.expert_soft.model.Cart;
import com.expert_soft.model.OrderItem;
import com.expert_soft.model.Phone;
import com.expert_soft.service.CartService;
import org.springframework.stereotype.Service;


@Service
public class CartServiceImpl implements CartService {

    @Override
    public boolean addToCart(Cart cart, Phone phone, Long quantity) {

        OrderItem currentItem = new OrderItem();
        currentItem.setPhone(phone);
        currentItem.setQuantity(quantity);

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

}
