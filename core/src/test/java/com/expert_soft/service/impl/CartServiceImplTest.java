package com.expert_soft.service.impl;

import com.expert_soft.model.OrderItem;
import com.expert_soft.model.Phone;
import com.expert_soft.model.calculator.OrderCalculatorImpl;
import com.expert_soft.model.order.Cart;
import com.expert_soft.test_util.DataBuilder;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import static com.expert_soft.test_util.MockData.getMockTrueValidator;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CartServiceImplTest {
    private static final Logger logger = Logger.getLogger(CartServiceImplTest.class);

    private static CartServiceImpl service;

    @BeforeClass
    public static void beforeClass(){
        service = new CartServiceImpl();
        service.setCalculator(Mockito.mock(OrderCalculatorImpl.class));
        service.setValidator(getMockTrueValidator());
    }

    @Test
    public void addToCart_ItemsExist() throws Exception {
        OrderItem item = DataBuilder.Order_1.getItem_1_new();
        Cart emptyCart = new Cart();

        service.addToCart(emptyCart, item.getPhone(), item.getQuantity());
        assertTrue(emptyCart.getOrderItems().contains(item));
    }

    @Test
    public void addToCart_SamePhone() throws Exception {
        Cart testedCart = new Cart();
        Phone phone = DataBuilder.getPhoneId_1();
        service.addToCart(testedCart, phone, 1);
        service.addToCart(testedCart, phone, 2);

        assertEquals("Fail to find same phone", testedCart.getOrderItems().size(), 1);
        OrderItem item1 = testedCart.getItem(phone.getKey());
        assertEquals("Fail to calculate new quantity",
                item1.getQuantity(), new Integer(3));
    }

    @Test
    public void deleteFromCart() throws Exception {
        Cart fullCart_2_items = DataBuilder.Carts.byOrder_2();
        service.deleteFromCart(fullCart_2_items, 1L);
        assertEquals(1, fullCart_2_items.getOrderItems().size());
    }

    @Test
    public void deleteFromCart1() throws Exception {
        Cart fullCart_2_items = DataBuilder.Carts.byOrder_2();
        service.deleteFromCart(fullCart_2_items, new Long[]{1L, 2L});
        assertTrue(fullCart_2_items.getOrderItems().isEmpty());
    }

    @Test
    public void updatePhoneQuantity() throws Exception {
        Cart fullCart_2_items = DataBuilder.Carts.byOrder_2();

        Integer expPhoneQuantity = 4;
        int sizeInitial = fullCart_2_items.getOrderItems().size();

        OrderItem item = new OrderItem(new Phone(1L), expPhoneQuantity);

        service.updatePhoneQuantity(fullCart_2_items, item);
        assertEquals("Quantity of order items in cart changed",
                sizeInitial, fullCart_2_items.getOrderItems().size());
        Integer realQuantity = fullCart_2_items.getItem(1L)
                                               .getQuantity();
        assertEquals(expPhoneQuantity, realQuantity);
    }

}