package com.expert_soft.model;


public class OrderItem {


    private Phone phone;
    private Order order;
    private Long quantity;

    public Phone getPhone() {
        return phone;
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

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
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

        if (phone != null ? !phone.equals(orderItem.phone) : orderItem.phone != null) {
            return false;
        }
        if (order != null ? !order.equals(orderItem.order) : orderItem.order != null) {
            return false;
        }
        return quantity != null ? quantity.equals(orderItem.quantity) : orderItem.quantity == null;

    }

    @Override
    public int hashCode() {
        int result = phone != null ? phone.hashCode() : 0;
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        return result;
    }
}
