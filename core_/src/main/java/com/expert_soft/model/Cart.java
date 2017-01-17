package com.expert_soft.model;


import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;


@Component
public class Cart implements Serializable{

    private HashMap<Long, OrderItem> itemsMap;
    private BigDecimal subtotal;

    public Cart() {
        itemsMap = new HashMap<>();
        subtotal = new BigDecimal("0");
    }

    public HashMap<Long, OrderItem> getItemsMap() {
        return itemsMap;
    }

    public void setItemsMap(HashMap<Long, OrderItem> itemsMap) {
        this.itemsMap = itemsMap;
    }

    public void addItem(OrderItem item){
        itemsMap.put(item.getUniqueKey(), item);
    }

    public void removeItem(OrderItem item){
        itemsMap.remove(item.getUniqueKey());
    }

    public boolean contains(OrderItem item){
        return itemsMap.containsKey(item.getUniqueKey());
    }

    public OrderItem getItem(OrderItem item){
        return itemsMap.get(item.getUniqueKey());
    }

    public Collection<OrderItem> getAllItems(){
        return itemsMap.values();
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cart{");
        sb.append("itemsMap=").append(itemsMap);
        sb.append(", subtotal=").append(subtotal);
        sb.append('}');
        return sb.toString();
    }
}
