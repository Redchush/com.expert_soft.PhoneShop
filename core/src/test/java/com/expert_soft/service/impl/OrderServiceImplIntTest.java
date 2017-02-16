package com.expert_soft.service.impl;

import com.expert_soft.model.order.Cart;
import com.expert_soft.model.order.Order;
import com.expert_soft.model.order.UserInfo;
import com.expert_soft.test_util.Context;
import com.expert_soft.test_util.DataBuilder;
import com.expert_soft.test_util.db.DbInfo;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.sql.DataSource;
import java.util.List;

import static com.expert_soft.test_util.asserts.ModelAsserts._assertEquals;
import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        Context.ROOT_WITH_CART
})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
})
@ActiveProfiles("test")
public class OrderServiceImplIntTest {

    private static final Logger logger = Logger.getLogger(OrderServiceImplIntTest.class);

    @Autowired private OrderServiceImpl service;
    @Autowired private DataSource source;

    private DbInfo rowCounter;
    private Order order;
    private Cart fullCart;

    private long orders;
    private long items;

    @Before
    public void setUp() throws Exception {
        order = DataBuilder.Order_2.getOrderByCart();
        fullCart = DataBuilder.Carts.byOrder_2();
        rowCounter = new DbInfo(new JdbcTemplate(source));
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
        Order saveExpected = DataBuilder.Order_1.getOrder_DB();
        Long key = service.saveOrder(saveExpected);
        Order actual = service.getOrder(key);

        assertTrue("Order not saved all",true);
        assertNotNull(actual);

        Order expected =
                DataBuilder.Order_1.getOrder(actual.getKey(), actual.getOrderItems()
                                                                    .get(0).getKey());
        _assertEquals(expected, actual);
    }
//
//    /**
//     * NOTE:THIS EXECUTES BEFORE SET_UP (@Before TEST ANNOTATION)
//     */
//    @BeforeTransaction
//    void beforeSaveOrder(){
//
//    }


    @Test
    public void findAll() throws Exception {
        List<Order> all = service.findAll();
        assertEquals(all.size(), rowCounter.getOrders());
    }

    @Test
    public void buildOrder() throws Exception {
        Order order = DataBuilder.Order_2.getOrderByCart();

        Order order_actual = service.buildOrder(fullCart, new UserInfo(),  false);
        assertEquals(order.getOrderItems(), order_actual.getOrderItems());
        _assertEquals(order, order_actual);
    }

    @Test
    public void buildOrderFull() throws Exception {
        order.setUserInfo(new UserInfo());
        fullCart.setSubtotal(order.getSubtotal());

        Order order_actual_1 = service.buildOrder(fullCart, new UserInfo(), false);
        Order order_actual_2 = service.buildOrder(fullCart, new UserInfo(), false);

        assertEquals(order_actual_2, order_actual_1);
    }

}