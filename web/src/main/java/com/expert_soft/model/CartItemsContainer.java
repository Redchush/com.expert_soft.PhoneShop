package com.expert_soft.model;


import com.expert_soft.validator.group.G_Cart;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;


@Validated(G_Cart.Item.class)
public class CartItemsContainer {

    private @Valid OrderItem[] items;

    public CartItemsContainer() {
        items = new OrderItem[0];
    }

    public CartItemsContainer(OrderItem[] items) {
        this.items = items;
    }

    public CartItemsContainer(int size) {
        items = new OrderItem[size];
        for (int i = 0; i < size; i++) {
            items[i] = new OrderItem();
            items[i].setPhone(new Phone());
        }
    }

    public OrderItem[] getItems() {
        return items;
    }

    public void setItems(OrderItem[] items) {
        this.items = items;
    }
}
