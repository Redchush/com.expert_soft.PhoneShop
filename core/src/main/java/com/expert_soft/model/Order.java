package com.expert_soft.model;


import org.springframework.beans.factory.InitializingBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Order {

    private Long key;
    private Set<OrderItem> orderItems;

    private BigDecimal deliveryPrice;

    private BigDecimal subtotal; // a sum of order item prices
    private BigDecimal totalPrice;

    private String firstName;
    private String lastName;
    private String deliveryAddress;
    private String contactPhoneNo;


    public Order() {
        orderItems = new HashSet<>();
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getContactPhoneNo() {
        return contactPhoneNo;
    }

    public void setContactPhoneNo(String contactPhoneNo) {
        this.contactPhoneNo = contactPhoneNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Order order = (Order) o;

        if (key != null ? !key.equals(order.key) : order.key != null) {
            return false;
        }
        if (orderItems != null ? !orderItems.equals(order.orderItems) : order.orderItems != null) {
            return false;
        }
        if (deliveryPrice != null ? !deliveryPrice.equals(order.deliveryPrice) : order.deliveryPrice != null) {
            return false;
        }
        if (firstName != null ? !firstName.equals(order.firstName) : order.firstName != null) {
            return false;
        }
        if (lastName != null ? !lastName.equals(order.lastName) : order.lastName != null) {
            return false;
        }
        if (deliveryAddress != null ? !deliveryAddress.equals(order.deliveryAddress) : order.deliveryAddress != null) {
            return false;
        }
        return contactPhoneNo != null ? contactPhoneNo.equals(order.contactPhoneNo) : order.contactPhoneNo == null;

    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (orderItems != null ? orderItems.hashCode() : 0);
        result = 31 * result + (deliveryPrice != null ? deliveryPrice.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (deliveryAddress != null ? deliveryAddress.hashCode() : 0);
        result = 31 * result + (contactPhoneNo != null ? contactPhoneNo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");

        sb.append("key=").append(key);
        sb.append(", orderItems=").append(orderItems);
        sb.append(", deliveryPrice=").append(deliveryPrice);
        sb.append(", subtotal=").append(subtotal);
        sb.append(", totalPrice=").append(totalPrice);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", deliveryAddress='").append(deliveryAddress).append('\'');
        sb.append(", contactPhoneNo='").append(contactPhoneNo).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public void initOrderInItems(){
        orderItems.forEach(s-> s.setOrder(this));
    }


}
