package com.expert_soft.model;


public class CartItemsContainer {

    private OrderItem[] items;

    public CartItemsContainer() {}

    public CartItemsContainer(OrderItem[] items) {
        this.items = items;
    }
    public CartItemsContainer(int size) {
        items = new OrderItem[size];
        for (int i = 0; i < size; i++) {
            items[i] = new OrderItem();
        }
    }

    public OrderItem[] getItems() {
        return items;
    }

    public void setItems(OrderItem[] items) {
        this.items = items;
    }
}
