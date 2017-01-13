package com.expert_soft.model;


import java.util.Collection;
import java.util.HashMap;

public class Cart {

    private HashMap<Long, OrderItem> itemsMap;

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


}
