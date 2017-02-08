package com.expert_soft.model.order;


import com.expert_soft.model.OrderItem;
import com.expert_soft.model.UserInfo;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class Order extends AbstractOrder{

    private Long key;
    private List<OrderItem> orderItems;

    private BigDecimal deliveryPrice;
    private BigDecimal totalPrice;

    private @Valid UserInfo userInfo;

    public Order() {
        super();
        orderItems = new ArrayList<>();
        userInfo = new UserInfo();
    }

    @Override
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public void addItem(OrderItem item) {
        orderItems.add(item);
    }

    @Override
    public void removeItem(OrderItem item) {
        orderItems.remove(item);
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
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

        return key != null ? key.equals(order.key) : order.key == null;

    }

    @Override
    public int hashCode() {
        return key != null ? key.hashCode() : 0;
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
