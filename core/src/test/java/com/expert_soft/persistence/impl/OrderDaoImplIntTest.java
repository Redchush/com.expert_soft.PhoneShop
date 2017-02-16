package com.expert_soft.persistence.impl;

import com.expert_soft.model.order.Order;
import com.expert_soft.model.order.OrderItem;
import com.expert_soft.model.order.OrderStatus;
import com.expert_soft.persistence.OrderDao;
import com.expert_soft.test_util.Context;
import com.expert_soft.test_util.DataBuilder;
import com.expert_soft.test_util.asserts.Comparators;
import com.expert_soft.test_util.db.DbInfo;
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
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.expert_soft.test_util.asserts.ModelAsserts._assertEquals;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        Context.ROOT_WITH_CART
})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class
})
@ActiveProfiles("test")
@Transactional
public class OrderDaoImplIntTest {

    @Autowired private OrderDao dao;
    @Autowired private DataSource dataSource;
    private DbInfo rowCounter;

    @Before
    public void setUp() throws Exception {
        rowCounter = new DbInfo(new JdbcTemplate(dataSource));
    }

    @Test
    public void getOrder() throws Exception {
        Order expected_1 = DataBuilder.Order_1.getOrder_DB();
        Order actual_1 = dao.getOrder(expected_1.getKey());
        _assertEquals(expected_1, actual_1);

        Order expected_2 = DataBuilder.Order_2.getOrder_DB();
        Order actual_2 = dao.getOrder(expected_2.getKey());
        _assertEquals("Fails to get order with multiple orderItems", expected_2, actual_2);
    }

    @Test
    public void findAll_checkOrder() throws Exception {
        Order order = dao.getOrder(4L);
        List<OrderItem> actual = order.getOrderItems();
        List<OrderItem> expected = new ArrayList<>(actual);
        Collections.sort(expected, new Comparators.OrderItemsByKey());
        assertEquals(actual, expected);
    }

    @Test
    public void saveOrder() throws Exception {
        Order saveExpected = DataBuilder.Order_1.getOrder_DB();
        Long key = dao.saveOrder(saveExpected);
        Order actual = dao.getOrder(key);
        Order expected =
                DataBuilder.Order_1.getOrder(actual.getKey(), actual.getOrderItems().get(0).getKey());
        assertTrue("Order not saved all",true);
        assertNotNull(actual);
        _assertEquals(expected, actual);
    }

    @Test
    public void findAll() throws Exception {
        List<Order> all = dao.findAll();
        assertEquals("Collected only: " + all.stream().map(s-> s.getKey() + "." + s.getFirstName()
                        + ":"+ s.getOrderItems().size() )
                                            .collect(Collectors.joining(" ")) + "\n ALL \n" + all,
                rowCounter.getOrders(), all.size());
    }

    @Test
    public void checkStatus() throws Exception{
        Order order = dao.getOrder(1L);
        assertEquals(order.getStatus(), OrderStatus.NEW);
    }

    @Test
    public void changeStatus() throws Exception {
        dao.changeStatus(2L, OrderStatus.DELIVERED);
        Order order = dao.getOrder(2L);
        assertEquals(order.getStatus(), OrderStatus.DELIVERED);
    }


}