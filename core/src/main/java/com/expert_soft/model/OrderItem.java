package com.expert_soft.model;


import com.expert_soft.validator.group.G_Cart;
import com.expert_soft.validator.group.G_Order;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class OrderItem {

    private Long key;

    private @Valid Phone phone;
    private Order order;

    private BigDecimal subtotal;

    @NotNull(message = "{orderItem.quantity.notNull}",
             groups = {G_Cart.Item.class, G_Order.Save.class})
    @Max(value = 10, message = "{orderItem.quantity.max}",
                     groups = G_Cart.Item.class)
    @Min(value = 1, message = "{orderItem.quantity.min}",
            groups = {G_Cart.Item.class, G_Order.Save.class})
    private Integer quantity;

    public OrderItem() {}

    @Valid
    public OrderItem(Phone phone, Integer quantity) {
        this.phone = phone;
        this.quantity = quantity;
    }

    public Phone getPhone() {
        return phone;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getUniqueKey(){
        return phone.getKey();
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal recalculateSubtotal(){
        return phone.getPrice().multiply(new BigDecimal(quantity));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderItem orderItem = (OrderItem) o;

        if (key != null ? !key.equals(orderItem.key) : orderItem.key != null) {
            return false;
        }
        if (phone != null ? !phone.equals(orderItem.phone) : orderItem.phone != null) {
            return false;
        }

        return quantity != null ? quantity.equals(orderItem.quantity) : orderItem.quantity == null;

    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderItem{");

        sb.append("key=").append(key);
        sb.append(", phone=").append(phone);
        sb.append(", order key=").append(order == null ? null : order.getKey());
        sb.append(", quantity=").append(quantity);
        sb.append('}');
        return sb.toString();
    }


}
