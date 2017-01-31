package com.expert_soft.model;


import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {

    private Long key;
    private List<OrderItem> orderItems;

    private BigDecimal deliveryPrice;
    private BigDecimal subtotal; // a sum of order item prices
    private BigDecimal totalPrice;

    private @Valid UserInfo userInfo;

    public Order() {
        orderItems = new ArrayList<>();
        userInfo = new UserInfo();
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
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


    public BigDecimal reculculateSubtotal(Calculator calculator){
        return calculator.calculateSubtotal(orderItems);
    }

    public BigDecimal calculateTotal(){
        return subtotal.add(deliveryPrice);
    }

    public Order calculateFull(Calculator calculator){
        BigDecimal subtotal = reculculateSubtotal(calculator);
        setSubtotal(subtotal);
        BigDecimal total = calculateTotal();
        setTotalPrice(total);
        return this;
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
