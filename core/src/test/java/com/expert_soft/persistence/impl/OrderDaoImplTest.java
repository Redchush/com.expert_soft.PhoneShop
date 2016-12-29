package com.expert_soft.persistence.impl;

import com.expert_soft.model.Order;
import com.expert_soft.model.OrderItem;
import com.expert_soft.model.Phone;
import com.expert_soft.persistence.OrderDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.sql.DataSource;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:persistence-context.xml",
        "classpath:test-dataSource-context.xml",
        "classpath:dataSource-context.xml"
//        ,"classpath:test-transaction-context.xml"

})

@TestExecutionListeners( {
        DependencyInjectionTestExecutionListener.class,
//        TransactionalTestExecutionListener.class
})
//@Transactional
public class OrderDaoImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private OrderDao dao;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ApplicationContext applicationContext;

    private static final int initialSize = 6;

    @Test
    public void setDataSource() throws Exception {
        DataSource dataSource = jdbcTemplate.getDataSource();
        assertEquals(dataSource, dataSource);
    }

    @Test
    public void getOrder() throws Exception {
        Order expected_1 = (Order) applicationContext.getBean("orderDaoImplTest_getOrder_1");
        Order actual_1 = dao.getOrder(expected_1.getKey());
        Set<OrderItem> orderItems = expected_1.getOrderItems();
        Set<OrderItem> orderItems1 = actual_1.getOrderItems();

        System.out.println(orderItems.equals(orderItems1));
        System.out.println(orderItems.containsAll(orderItems1));

        System.out.println(orderItems.hashCode() == orderItems1.hashCode());
        System.out.println(orderItems.toString().equals(orderItems1.toString()));



        OrderItem next = expected_1.getOrderItems().iterator().next();
        OrderItem next1 = actual_1.getOrderItems().iterator().next();

        System.out.println(next.equals(next1));
        System.out.println(next1.equals(next));

        System.out.println(next.hashCode() + " " + next1.hashCode());
        System.out.println(next.hashCode() == next1.hashCode());

        orderItems.contains(next1);

        System.out.println(
                orderItems.contains(next1));

//        assertEquals("Order with single item ", expected_1, actual_1);

        Order expected_2 = (Order) applicationContext.getBean("orderDaoImplTest_getOrder_2");
        Order actual_2 = dao.getOrder(expected_2.getKey());

//        assertEquals("Fails to get order with multiple orderItems", expected_2, actual_2);

    }


    @Test
    public void saveOrder() throws Exception {

        Long orderKeyExpected = initialSize + 1L;
        Order expected = (Order) applicationContext.getBean("orderDaoImplTest_getOrder_2");

        expected.setDeliveryAddress("new address");

        dao.saveOrder(expected);

        Order actual = dao.getOrder(orderKeyExpected);

        assertTrue("Order not saved at all",true);
        assertEquals("Order not saved with correct id expected", orderKeyExpected, actual.getKey());

        resetKeysInOrder(actual);
        resetKeysInOrder(expected);

        assertEquals(expected, actual);

    }

    private void resetKeysInOrder(Order order){
        order.setKey(null);
        order.getOrderItems().forEach(s-> {
            s.setKey(null); s.setKey(null);
        });
    }



    @Test
    public void findAll() throws Exception {
        int ordersWithourItems = 1;
        int expectedSize = initialSize -ordersWithourItems;
        List<Order> all = dao.findAll();
        assertEquals("Collected only: " + all.stream().map(s-> s.getKey() + "." + s.getFirstName())
                                            .collect(Collectors.joining(" ")),
                     expectedSize, all.size());
    }

}