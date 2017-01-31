package com.expert_soft.util;


import com.expert_soft.model.order.Cart;
import com.expert_soft.model.order.Order;
import com.expert_soft.model.OrderItem;
import com.expert_soft.model.Phone;
import com.expert_soft.model.UserInfo;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class DataBuilder {

    public static Cart buildCartWithoutSubtotal(Order order){
        Cart cart = new Cart();
        Map<Long, OrderItem> collect =
                               order.getOrderItems()
                              .stream()
                              .collect(Collectors.toMap(s->s.getPhone().getKey(), s->s));
        cart.setItemsMap(collect);
        return cart;
    }

    public static Phone getPhoneId_1(){
        BigDecimal price = new BigDecimal("111.11");
        return new Phone(1L, "fly", "white", 11, price);
    }

    public static Phone getPhoneId_2(){
        BigDecimal price = new BigDecimal("222.22");
        return new Phone(2L, "samsung", "white", 22, price);
    }

    public static Phone getPhoneToSave(){
        BigDecimal price = new BigDecimal("111.11");
        return new Phone(null, "new_phone_1", "white", 11, price);
    }

    public static Phone getPhoneToSave(Long id){
        BigDecimal price = new BigDecimal("111.11");
        return new Phone(id, "new_phone_2", "white", 11, price);
    }

    public static class Order_1{

        public static OrderItem getItem_1(){
            return new OrderItem(getPhoneId_1(), 1);
        }

        public static OrderItem getItem_1_DB(Order order){
            OrderItem item = new OrderItem(getPhoneId_1(), 1);
            item.setKey(1L);
            item.setOrder(order);
            return item;
        }

        public static OrderItem getItem_1_DB(Order order, Long id){
            OrderItem item = new OrderItem(getPhoneId_1(), 1);
            item.setKey(id);
            item.setOrder(order);
            return item;
        }

        public static Order getOrder(){
            Order result = new Order();
            result.setDeliveryPrice(new BigDecimal("5.00"));
            OrderItem item_1 = getItem_1();
            item_1.setKey(13L);
            result.setUserInfo(getUserInfo());
            result.setOrderItems(Collections.singletonList(item_1));
            return result;
        }

        public static Order getOrder(Long id, Long itemId){
            Order result = templateOrder();
            result.setKey(id);
            OrderItem item_1_db = getItem_1_DB(result, itemId);
            result.setOrderItems(Collections.singletonList(item_1_db));
            return result;
        }

        public static Order getOrder_DB(){
            Order result = templateOrder();
            result.setKey(1L);
            OrderItem item_1 = getItem_1_DB(result);
            result.setOrderItems(Collections.singletonList(item_1));
            return result;
        }

        public static UserInfo getUserInfo(){
            UserInfo result = new UserInfo();
            result.setFirstName("klara");
            result.setLastName("karlovna");
            result.setDeliveryAddress("karlovy vary");
            result.setContactPhoneNo("+375 29 111 77 77");
            return result;
        }

        private static Order templateOrder(){
            Order result = new Order();
            result.setDeliveryPrice(new BigDecimal("5.00"));
            result.setUserInfo(getUserInfo());
            result.setTotalPrice(new BigDecimal("116.11"));
            result.setSubtotal(new BigDecimal("111.11"));
            return result;
        }
    }

    public static class Order_2{

        public static OrderItem getItem_1(){
            return new OrderItem(getPhoneId_1(), 1);
        }

        public static OrderItem getItem_1_DB(Order order){
            OrderItem result = getItem_1();
            result.setOrder(order);
            result.setKey(2L);
            return result;
        }

        public static OrderItem getItem_2(){
            return new OrderItem(getPhoneId_2(), 1);
        }

        public static OrderItem getItem_2_DB(Order order){
            OrderItem result = getItem_2();
            result.setOrder(order);
            result.setKey(3L);
            return result;
        }

        public static Order getOrder_DB(){
            Order result = getTemplateOrder();
            result.setKey(2L);
            result.setUserInfo(getUserInfo());

            List<OrderItem> items = new ArrayList<>();
            items.add(getItem_1_DB(result));
            items.add(getItem_2_DB(result));
            result.setOrderItems(items);

            return result;
        }

        public static Order getOrder(){
            Order result = getTemplateOrder();
            result.setDeliveryPrice(new BigDecimal("5.00"));
            result.setUserInfo(getUserInfo());
            List<OrderItem> items = new ArrayList<>();
            items.add(getItem_1());
            items.add(getItem_2());
            result.setOrderItems(items);
            return result;
        }

        public static Order getOrderByCart(){
            Order result = getOrder();
            result.setUserInfo(new UserInfo());
            return result;
        }

        public static UserInfo getUserInfo(){
            UserInfo result = new UserInfo();
            result.setFirstName("kenny");
            result.setLastName("poor");
            result.setDeliveryAddress("south park");
            result.setContactPhoneNo("+375 29 222 77 77");
            return result;
        }

        private static Order getTemplateOrder(){
            Order result = new Order();
            result.setDeliveryPrice(new BigDecimal("5.00"));
            result.setUserInfo(getUserInfo());
            result.setTotalPrice(new BigDecimal("338.33"));
            result.setSubtotal(new BigDecimal("333.33"));
            return result;
        }
    }

    public static class Carts{
        public static Cart withOneItem(){
            Cart cart = new Cart();
            return cart;
        }

        public static Cart byOrder_2() {
            Cart cart = new Cart();
            cart.setCartSize(2);
            cart.setSubtotal(new BigDecimal("333.33"));
            Map<Long, OrderItem> map = new HashMap<>();
            map.put(1L, Order_2.getItem_1());
            map.put(2L, Order_2.getItem_2());

            cart.setItemsMap(map);
            return cart;
        }

        public static Cart byOrder_1(){
            Cart cart = new Cart();
            cart.setCartSize(2);
            cart.setSubtotal(Order_1.getItem_1().getPhone().getPrice());
            Map<Long, OrderItem> map = new HashMap<>();
            map.put(1L, Order_1.getItem_1());
            cart.setItemsMap(map);
            return cart;
        }
    }

    public static class Validation{

        public static Properties getValidationMsgs() throws IOException {
            Properties result =new Properties();
            InputStream resourceAsStream =
                         Validation.class.getClassLoader()
                        .getResourceAsStream("ValidationMessages.properties");
            result.load(resourceAsStream);
            return result;
        }
    }



}
