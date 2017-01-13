package com.expert_soft.persistence.impl;

import com.expert_soft.model.Order;
import com.expert_soft.persistence.OrderDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:persistence-context.xml",
        "classpath:test-dataSource-context.xml",
        "classpath:test-data.xml"
})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class
})
@Transactional
public class OrderDaoImplTest {

    @Autowired
    private OrderDao dao;

    @Autowired
    private ApplicationContext applicationContext;

    private static final int initialSize = 6;

    @Test
    public void setDataSource() throws Exception {

    }

    @Test
    public void getOrder() throws Exception {
        Order expected_1 = (Order) applicationContext.getBean("orderDaoImplTest_getOrder_1");
        Order actual_1 = dao.getOrder(expected_1.getKey());
        assertEquals(expected_1, actual_1);

        Order expected_2 = (Order) applicationContext.getBean("orderDaoImplTest_getOrder_2");
        Order actual_2 = dao.getOrder(expected_2.getKey());
        assertEquals("Fails to get order with multiple orderItems", expected_2, actual_2);
    }


    @Test
    public void saveOrder() throws Exception {

        Long orderKeyExpected = initialSize + 1L;
        Order expected = (Order) applicationContext.getBean("saveOrder_1");
        dao.saveOrder(expected);

        Order actual = dao.getOrder(orderKeyExpected);
        assertTrue("Order not saved at all",true);
        assertEquals("Order not saved with correct id expected", orderKeyExpected, actual.getKey());
        assertEquals(expected, actual);

    }


    @Test
    public void findAll() throws Exception {
        List<Order> all = dao.findAll();
        assertEquals("Collected only: " + all.stream().map(s-> s.getKey() + "." + s.getFirstName()
                        + ":"+ s.getOrderItems().size() )
                                            .collect(Collectors.joining(" ")) + "\n ALL \n" + all,
                initialSize, all.size());
    }

}