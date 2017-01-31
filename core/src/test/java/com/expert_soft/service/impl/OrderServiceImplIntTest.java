package com.expert_soft.service.impl;

import com.expert_soft.model.*;
import com.expert_soft.service.OrderService;
import com.expert_soft.util.db.CountRowResponsible;
import com.expert_soft.util.DataBuilder;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.sql.DataSource;
import java.util.List;

import static com.expert_soft.util.asserts.ModelAsserts._assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:context/core_root-context.xml"
})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
})
@ActiveProfiles("dev")
public class OrderServiceImplIntTest {

    private static final Logger logger = Logger.getLogger(CartServiceImplTest.class);

    @Autowired private OrderService service;
    @Autowired private Calculator calculator;
    @Autowired private DataSource source;

    private CountRowResponsible rowCounter;

    private Cart emptyCart;
    private Order order;
    private Cart fullCart;

    @Before
    public void setUp() throws Exception {
        emptyCart = new Cart();
        order = DataBuilder.Order_2.getOrderCalculated();
        fullCart = DataBuilder.Carts.byOrder_2();
        rowCounter = new CountRowResponsible(new JdbcTemplate(source));
    }

    @Test
    public void getOrder() throws Exception {
        Order expected_1 = DataBuilder.Order_1.getOrder_DB();
        Order actual_1 = service.getOrder(expected_1.getKey());
        _assertEquals(expected_1, actual_1);

        Order expected_2 = DataBuilder.Order_1.getOrder_DB();
        Order actual_2 = service.getOrder(expected_2.getKey());
        _assertEquals("Fails to get order with multiple orderItems", expected_2, actual_2);
    }

    @Test
    public void saveOrder() throws Exception {
        long orders = rowCounter.getOrders() +1L;
        long items = rowCounter.getOrderItems() + 1L;
        Order expected = DataBuilder.Order_1.getOrder(orders,  items);
        Long aLong = service.saveOrder(expected);

        Order actual = service.getOrder(aLong);
        assertTrue("Order not saved at all",true);
        assertEquals("Order not saved with correct id expected", aLong, actual.getKey());
        _assertEquals(expected, actual);
    }

    @Test
    public void findAll() throws Exception {
        List<Order> all = service.findAll();
        assertEquals(all.size(), rowCounter.getOrders());
    }

    @Test
    public void buildOrder() throws Exception {
        Order order = DataBuilder.Order_2.getOrderCalculated();

        Order order_actual = service.buildOrder(fullCart, false);
        assertEquals(order.getOrderItems(), order_actual.getOrderItems());
        _assertEquals(order, order_actual);
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