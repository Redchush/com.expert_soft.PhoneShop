package com.expert_soft.model.order;


import com.expert_soft.model.OrderItem;
import com.expert_soft.persistence.impl.util.Collectors;
import com.expert_soft.validator.group.G_Cart;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

public class Cart extends AbstractOrder implements Serializable{

    private static final long serialVersionUID = 1L;

    @Valid
    @NotNull(groups = G_Cart.Item.class)
    private Map<Long, OrderItem> orderItems;

    private Integer totalPhonesCount;

    public Cart() {
        super();
        orderItems = new HashMap<>(1);
    }

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

    public OrderItem removeByPhoneKey(Long phoneKey){
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cart{");
        sb.append(", subtotal=").append(subtotal);
        sb.append(", totalPhonesCount=").append(totalPhonesCount);
        sb.append(", orderItems=").append(orderItems);
        sb.append('}');
        return sb.toString();
    }
}
