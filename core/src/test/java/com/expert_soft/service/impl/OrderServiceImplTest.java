package com.expert_soft.service.impl;

import com.expert_soft.model.*;
import com.expert_soft.service.OrderService;
import com.expert_soft.util.CountRowResponsible;
import com.expert_soft.util.DataBuilder;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:common_context.xml",
        "classpath:persistence-context.xml",
        "classpath:test-dataSource-context.xml",
        "classpath:core_test-bean.xml",
        "classpath:service-context.xml"
})
@TestPropertySource(locations =
        "classpath:config/applicationTest.properties")
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
})
public class OrderServiceImplTest {

    private static final Logger logger = Logger.getLogger(CartServiceImplTest.class);

    @Autowired
    private OrderService service;

    @Autowired
    private ApplicationContext ac;

    @Autowired
    private Calculator calculator;

    @Autowired
    private CountRowResponsible schema;

    @Autowired
    @Qualifier("orderItem_secondOrder_1_new")
    private OrderItem firstItem;
    @Autowired
    @Qualifier("orderItem_secondOrder_2_new")
    private OrderItem secondItem;

    private Cart emptyCart;
    private Order order;
    private Cart fullCart;

    @Before
    public void setUp() throws Exception {
        emptyCart = new Cart();
        order = (Order) ac.getBean("order_2_new_calculated");
        HashSet<OrderItem> orderItems = new HashSet<OrderItem>(Arrays.asList(firstItem, secondItem));
        order.setOrderItems(orderItems);
        fullCart = DataBuilder.buildCartWithoutSubtotal(order);
    }

    @Test
    public void setDao() throws Exception {

    }

    @Test
    public void getOrder() throws Exception {
        Order expected_1 = (Order) ac.getBean("order_1");
        Order actual_1 = service.getOrder(expected_1.getKey());
        assertEquals(expected_1, actual_1);

        Order expected_2 = (Order) ac.getBean("order_2_db");
        Order actual_2 = service.getOrder(expected_2.getKey());
        assertEquals("Fails to get order with multiple orderItems", expected_2, actual_2);
    }

    @Test
    public void saveOrder() throws Exception {
        Order expected = (Order) ac.getBean("saveOrder_1");
        Long aLong = service.saveOrder(expected);

        Order actual = service.getOrder(aLong);
        assertTrue("Order not saved at all",true);
        assertEquals("Order not saved with correct id expected", aLong, actual.getKey());
        assertEquals(expected, actual);
    }

    @Test
    public void findAll() throws Exception {
        List<Order> all = service.findAll();
        assertEquals(all.size(), schema.getOrders());
    }

    @Test
    public void buildOrder() throws Exception {
        order.setUserInfo(new UserInfo());

        fullCart.setSubtotal(order.getSubtotal());
        Order order_actual = service.buildOrder(fullCart, false);

        assertEquals(order.getOrderItems(), order_actual.getOrderItems());
        assertEquals(order, order_actual);

    }

    @Test
    public void buildOrderFull() throws Exception {
        order.setUserInfo(new UserInfo());
        fullCart.setSubtotal(order.getSubtotal());

        Order order_actual_1 = service.buildOrder(fullCart, false);
        Order order_actual_2 = service.buildOrder(fullCart, new UserInfo(), false);

        assertEquals(order_actual_2, order_actual_1);
    }

}