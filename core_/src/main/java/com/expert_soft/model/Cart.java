package com.expert_soft.model;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Cart implements Serializable{

    public static final Cart EMPTY_CART = new Cart();

    private ConcurrentMap<Long, OrderItem> itemsMap;
    private BigDecimal subtotal;
    private Integer cartSize;

    public Cart() {
        itemsMap = new ConcurrentHashMap<>(1);
        subtotal = new BigDecimal("0");
    }

    public ConcurrentMap<Long, OrderItem> getItemsMap() {
        return itemsMap;
    }

    public void setItemsMap(ConcurrentMap<Long, OrderItem> itemsMap) {
        this.itemsMap = itemsMap;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal recalculateSubtotal(Calculator calculator){
        return calculator.calculateSubtotal(itemsMap.values());
    }

    public Integer getCartSize() {
        return cartSize;
    }

    public void setCartSize(Integer cartSize) {
        this.cartSize = cartSize;
    }

    public int totalItemsCount(){
        int result = 0;
        for (OrderItem item: itemsMap.values()){
            result+= item.getQuantity();
        }
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cart{");
        sb.append("itemsMap=").append(itemsMap);
        sb.append(", subtotal=").append(subtotal);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Cart cart = (Cart) o;

        if (itemsMap != null ? !itemsMap.equals(cart.itemsMap) : cart.itemsMap != null) {
            return false;
        }
        return subtotal != null ? subtotal.equals(cart.subtotal) : cart.subtotal == null;

    }

    @Override
    public int hashCode() {
        int result = itemsMap != null ? itemsMap.hashCode() : 0;
        result = 31 * result + (subtotal != null ? subtotal.hashCode() : 0);
        return result;
    }
}
