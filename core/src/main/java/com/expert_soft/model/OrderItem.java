package com.expert_soft.model;


public class OrderItem {

    private Long key;
    private Phone phone;
    private Order order;
    private Long quantity;

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

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getUniqueKey(){
        return phone.getKey();
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
        if (order != null ? (order.getKey() != null && key == null) ||
                            !order.getKey().equals(orderItem.order.getKey())
                          : orderItem.order.getKey() != null) {
            return false;
        }
        return quantity != null ? quantity.equals(orderItem.quantity) : orderItem.quantity == null;

    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (order != null ? (order.getKey() != null ?  order.getKey().hashCode()
                                                                        : 0)
                                              : 0);
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
