package com.expert_soft.service.impl;

import com.expert_soft.model.Cart;
import com.expert_soft.model.Order;
import com.expert_soft.model.OrderItem;
import com.expert_soft.model.excluded.CartCurriculum;
import com.expert_soft.service.CartService;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import util.DataBuilder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:persistence-context.xml",
        "classpath:test-dataSource-context.xml",
        "classpath:core_test-bean.xml",
        "classpath:service-context.xml"
})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
})
public class CartServiceImplTest {


    private static final Logger logger = Logger.getLogger(CartServiceImplTest.class);

    @Autowired
    private CartService service;

    @Autowired
    private ApplicationContext ac;

    @Autowired
    @Qualifier("orderItem_secondOrder_1_db")
    private OrderItem firstItem;

    @Autowired
    @Qualifier("orderItem_secondOrder_2_db")
    private OrderItem secondItem;

    private Cart emptyCart;
    private Order order;
    private Cart fullCart;

    @Before
    public void setUp() throws Exception {
        emptyCart = new Cart();
        order = (Order) ac.getBean("order_2_new_calculated");
        HashSet<OrderItem> orderItems = new HashSet<>(Arrays.asList(firstItem, secondItem));
        order.setOrderItems(orderItems);

        fullCart = DataBuilder.buildCartWithoutSubtotal(order);

        firstItem.setOrder(null);
        secondItem.setOrder(null);
        firstItem.setKey(null);
        secondItem.setKey(null);

    }

    @Test
    public void addToCart() throws Exception {
        Cart cart = new Cart();
        cart.getItemsMap().putIfAbsent(firstItem.getPhone().getKey(), firstItem);

        service.addToCart(emptyCart, firstItem.getPhone().getKey(), firstItem.getQuantity());
        assertEquals(cart, emptyCart);

        OrderItem item = new OrderItem();
        item.setPhone(firstItem.getPhone());
        service.addToCart(cart, firstItem.getPhone().getKey(), 1);
        assertEquals(cart.getItemsMap().size(), 1);
        assertEquals("Fail to calculate new quantity",
                      cart.getItemsMap().get(firstItem.getPhone().getKey()).getQuantity(),
                      new Integer(2));

    }

    @Test
    public void addToCart1() throws Exception {
        Cart cart = new Cart();
        cart.getItemsMap().putIfAbsent(firstItem.getPhone().getKey(), firstItem);

        service.addToCart(emptyCart, firstItem.getPhone(), firstItem.getQuantity());
        assertEquals(cart, emptyCart);

        OrderItem item = new OrderItem();
        item.setPhone(firstItem.getPhone());
        service.addToCart(cart, firstItem.getPhone(), 1);
        assertEquals(cart.getItemsMap().size(), 1);

        OrderItem item1 = cart.getItemsMap().get(firstItem.getPhone().getKey());
        assertEquals("Fail to calculate new quantity",
                item1.getQuantity(),
                new Integer(2));

    }

    @Test
    public void deleteFromCart() throws Exception {
        service.deleteFromCart(fullCart, 1L);
        assertEquals(fullCart.getItemsMap().size(), 1);
    }

    @Test
    public void deleteFromCart1() throws Exception {
        service.deleteFromCart(fullCart, new Long[]{1L, 2L});
        assertTrue(fullCart.getItemsMap().isEmpty());
    }

    @Test
    public void changeQuantity() throws Exception {
        Integer expected=4;
        service.changeQuantity(fullCart, 1L, expected);
        Integer realQuantity = fullCart.getItemsMap().get(1L).getQuantity();
        assertEquals(expected, realQuantity);
    }

    @Test
    public void calculateAndSetSubtotal() throws Exception {
        BigDecimal actual = service.calculateAndSetSubtotal(fullCart);
        assertEquals(order.getSubtotal(), actual);

        Cart cart = new Cart();
        cart.getItemsMap().putIfAbsent(firstItem.getPhone().getKey(), firstItem);
        BigDecimal actual_2 = service.calculateAndSetSubtotal(cart);
        assertEquals(firstItem.getPhone().getPrice(), actual_2);
    }
    @Test
    public void buildCurriculum() throws Exception {
        CartCurriculum expected = ac.getBean("curriculum_order_2", CartCurriculum.class);
        CartCurriculum actual = service.buildCurriculum(fullCart);

        assertEquals("cart size invalid calculation. Cart tested: " +
                fullCart, expected.getCartSize(), actual.getCartSize());
        assertEquals("cart subtotal invalid calculation. Cart tested: " +
                fullCart, expected.getCartSubtotal(), actual.getCartSubtotal());
    }

}