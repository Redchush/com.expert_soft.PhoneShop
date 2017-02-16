package com.expert_soft.service.impl;

import com.expert_soft.model.Phone;
import com.expert_soft.model.calculator.impl.OrderCalculatorImpl;
import com.expert_soft.model.order.Cart;
import com.expert_soft.model.order.OrderItem;
import com.expert_soft.test_util.DataBuilder;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import static com.expert_soft.test_util.MockData.getMockTrueValidator;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CartServiceImplTest {
    private static final Logger logger = Logger.getLogger(CartServiceImplTest.class);

    private static CartServiceImpl service;
    private Cart actual;

    @BeforeClass
    public static void beforeClass(){
        service = new CartServiceImpl();
        service.setCalculator(Mockito.mock(OrderCalculatorImpl.class));
        service.setValidator(getMockTrueValidator());
    }

    @Before
    public void setUp() throws Exception {
        service.setCart(new Cart());
        actual = service.getCart();
    }

    @Test
    public void addToCart_ItemsExist() throws Exception {
        OrderItem item = DataBuilder.Order_1.getItem_1_new();

        service.addToCart(item.getPhone(), item.getQuantity());
        assertTrue(actual.getOrderItems().contains(item));
    }

    @Test
    public void addToCart_SamePhone() throws Exception {
        Phone phone = DataBuilder.getPhoneId_1();
        service.addToCart(phone, 1);
        service.addToCart(phone, 2);

        assertEquals("Fail to find same phone", actual.getOrderItems().size(), 1);
        OrderItem item1 = actual.getItem(phone.getKey());
        assertEquals("Fail to calculate new quantity",
                item1.getQuantity(), new Integer(3));
    }

    @Test
    public void deleteFromCart() throws Exception {
        Cart fullCart_2_items = DataBuilder.Carts.byOrder_2();
        service.setCart(fullCart_2_items);

        service.deleteFromCart(1L);
        assertEquals(1, fullCart_2_items.getOrderItems().size());
    }

    @Test
    public void deleteFromCart1() throws Exception {
        Cart fullCart_2_items = DataBuilder.Carts.byOrder_2();
        service.setCart(fullCart_2_items);

        service.deleteFromCart(new Long[]{1L, 2L});
        assertTrue(fullCart_2_items.getOrderItems().isEmpty());
    }

    @Test
    public void updatePhoneQuantity() throws Exception {
        Cart testedCart = DataBuilder.Carts.byOrder_2();
        service.setCart(testedCart);

        Integer expPhoneQuantity = 4;
        int sizeInitial = testedCart.getOrderItems().size();

        service.updatePhoneQuantity(1L, expPhoneQuantity);
        assertEquals("Quantity of order items in cart changed",
                sizeInitial, testedCart.getOrderItems().size());
        Integer realQuantity = testedCart.getItem(1L)
                                               .getQuantity();
        assertEquals(expPhoneQuantity, realQuantity);
    }

}