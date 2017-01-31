package com.expert_soft.util.asserts;


import com.expert_soft.model.OrderItem;
import com.expert_soft.model.order.Cart;
import com.expert_soft.model.order.Order;
import com.expert_soft.model.Phone;

import java.util.ArrayList;
import java.util.Collection;

public class Comparators {

    /**
     * NOTE: map.values() return own implementation of AbstractCollection, where equals not
     * implemented
     */
    public static boolean equals(Cart o1, Cart o2){

        if (o1 == o2) {
            return true;
        }
        if (o2 == null || o1.getClass() != o2.getClass()) {
            return false;
        }
        Collection<OrderItem> orderItems_1 = o1.getOrderItems();
        if ( orderItems_1 != null ?
            !new ArrayList<>(orderItems_1).equals(new ArrayList<>(o2.getOrderItems()))
                                       : o2.getOrderItems() != null) {
            return false;
        }
        if (o1.getSubtotal() != null ?
            !o1.getSubtotal().equals(o2.getSubtotal())
                                     : o2.getSubtotal() != null) {
            return false;
        }
        return o1.getTotalPhonesCount() != null ?
               o1.getTotalPhonesCount().equals(o2.getTotalPhonesCount()) :
               o2.getTotalPhonesCount() == null;

    }

    public static boolean equals(Order o1, Order o2){
        if (o1 == o2) {
            return true;
        }
        if (o1 == null || o1.getClass() != o2.getClass()) {
            return false;
        }

        if (o1.getKey() != null ? !o1.getKey().equals(o2.getKey()) : o2.getKey() != null) {
            return false;
        }
        if (o1.getOrderItems() != null ? !o1.getOrderItems().equals(o2.getOrderItems()) : o2.getOrderItems() != null) {
            return false;
        }
        if (o1.getDeliveryPrice() != null ? !o1.getDeliveryPrice().equals(o2.getDeliveryPrice())
                                          : o2.getDeliveryPrice() != null) {
            return false;
        }
        if (o1.getSubtotal() != null ? !o1.getSubtotal().equals(o2.getSubtotal()) : o2.getSubtotal() != null) {
            return false;
        }
        if (o1.getTotalPrice() != null ? !o1.getTotalPrice().equals(o2.getTotalPrice()) : o2.getTotalPrice() != null) {
            return false;
        }
        return o1.getUserInfo() != null ? o1.getUserInfo().equals(o2.getUserInfo())
                                        : o2.getUserInfo() == null;
    }


    public static boolean equals(Phone o1, Phone o2) {
        if (o1 == o2) {
            return true;
        }
        if (o1 == null || o1.getClass() != o1.getClass()) {
            return false;
        }

        if (o1.getKey() != null ? !o1.getKey().equals(o2.getKey()) : o2.getKey() != null) {
            return false;
        }
        if (o1.getModel() != null ? !o1.getModel().equals(o2.getModel()) : o2.getModel() != null) {
            return false;
        }
        if (o1.getColor() != null ? !o1.getColor().equals(o2.getColor()) : o2.getColor() != null) {
            return false;
        }
        if (o1.getDisplaySize() != null ? !o1.getDisplaySize().equals(o2.getDisplaySize())
                                        : o2.getDisplaySize() != null) {
            return false;
        }
        if (o1.getPrice() != null ? !o1.getPrice().equals(o2.getPrice()) : o2.getPrice() != null) {
            return false;
        }
        if (o1.getWidth() != null ? !o1.getWidth().equals(o2.getWidth()) : o2.getWidth() != null) {
            return false;
        }
        if (o1.getLength() != null ? !o1.getLength().equals(o2.getLength()) : o2.getLength() != null) {
            return false;
        }
        return o1.getCamera() != null ? o1.getCamera().equals(o2.getCamera()) : o2.getCamera() == null;
    }


}
