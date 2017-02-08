package com.expert_soft.model.order;


import com.expert_soft.model.OrderItem;
import com.expert_soft.validator.group.G_Cart;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Cart extends AbstractOrder implements Serializable {


    private static final long serialVersionUID = 1L;

    private Long id;

    @Valid
    @NotNull(groups = G_Cart.Item.class)
    private Map<Long, OrderItem> orderItems;

    private Integer totalPhonesCount;

    public Cart() {
        super();
        orderItems = new HashMap<>(1);
    }

    @Override
    public Collection<OrderItem> getOrderItems() {
        return Collections.unmodifiableCollection(orderItems.values());
    }


    public Collection<Long> getPhoneKeys(){
        return Collections.unmodifiableSet(orderItems.keySet());
    }

    //    method for access order items
    @Override
    public void addItem(OrderItem item){
        orderItems.put(item.getPhone().getKey(), item);
    }
    @Override
    public void removeItem(OrderItem item){
        orderItems.remove(item.getPhone().getKey());
    }

    public OrderItem removeItem(Long phoneKey){
        return orderItems.remove(phoneKey);
    }

    public OrderItem getItem(Long phoneKey){
        return orderItems.get(phoneKey);
    }

    public Integer getTotalPhonesCount() {
        return totalPhonesCount;
    }

    public void setTotalPhonesCount(Integer totalPhonesCount) {
        this.totalPhonesCount = totalPhonesCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CartImpl{");
        sb.append(super.toString()).append(" ");
        sb.append("id=").append(id);
        sb.append(", orderItems=").append(orderItems);
        sb.append(", totalPhonesCount=").append(totalPhonesCount);
        sb.append('}');
        return sb.toString();
    }
}
