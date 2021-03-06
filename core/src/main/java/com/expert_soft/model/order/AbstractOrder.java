package com.expert_soft.model.order;


import java.math.BigDecimal;
import java.util.Collection;

public abstract class AbstractOrder {

    protected BigDecimal subtotal;

    public AbstractOrder() {}

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public abstract Collection<OrderItem> getOrderItems();

    public abstract void addItem(OrderItem item);

    public abstract void removeItem(OrderItem item);

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AbstractOrder{");
        sb.append("subtotal=").append(subtotal);
        sb.append('}');
        return sb.toString();
    }
}
