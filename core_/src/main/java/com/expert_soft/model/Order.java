package com.expert_soft.model;


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


public class Order {

    private Long key;
    private Set<OrderItem> orderItems;

    private BigDecimal deliveryPrice;
    private BigDecimal subtotal; // a sum of order item prices
    private BigDecimal totalPrice;

    private UserInfo userInfo;

    public Order() {
        orderItems = new HashSet<>();
        userInfo = new UserInfo();
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

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getFirstName() {
        return userInfo.getFirstName();
    }

    public void setFirstName(String firstName) {
        userInfo.setFirstName(firstName);
    }

    public String getLastName() {
        return userInfo.getLastName();
    }

    public void setLastName(String lastName) {
        userInfo.setLastName(lastName);
    }

    public String getDeliveryAddress() {
        return userInfo.getDeliveryAddress();
    }

    public void setDeliveryAddress(String deliveryAddress) {
        userInfo.setDeliveryAddress(deliveryAddress);
    }

    public String getContactPhoneNo() {
        return userInfo.getContactPhoneNo();
    }

    public void setContactPhoneNo(String contactPhoneNo) {
        userInfo.setContactPhoneNo(contactPhoneNo);
    }

    public String getAdditionalInfo() {
        return userInfo.getAdditionalInfo();
    }

    public void setAdditionalInfo(String additionalInfo) {
        userInfo.setAdditionalInfo(additionalInfo);
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
        if (subtotal != null ? !subtotal.equals(order.subtotal) : order.subtotal != null) {
            return false;
        }
        if (totalPrice != null ? !totalPrice.equals(order.totalPrice) : order.totalPrice != null) {
            return false;
        }
        return userInfo != null ? userInfo.equals(order.userInfo) : order.userInfo == null;

    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (orderItems != null ? orderItems.hashCode() : 0);
        result = 31 * result + (deliveryPrice != null ? deliveryPrice.hashCode() : 0);
        result = 31 * result + (subtotal != null ? subtotal.hashCode() : 0);
        result = 31 * result + (totalPrice != null ? totalPrice.hashCode() : 0);
        result = 31 * result + (userInfo != null ? userInfo.hashCode() : 0);
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
        sb.append(", userInfo=").append(userInfo);
        sb.append('}');
        return sb.toString();
    }


}
