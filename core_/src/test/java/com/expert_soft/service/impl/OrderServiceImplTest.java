package com.expert_soft.service.impl;

import com.expert_soft.model.Order;
import com.expert_soft.service.OrderService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:persistence-context.xml",
        "classpath:test-dataSource-context.xml",
        "classpath:test-data.xml",
        "classpath:service-context.xml"
})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
})
public class OrderServiceImplTest {

    private static final Logger logger = Logger.getLogger(CartServiceImplTest.class);

    @Autowired
    private OrderService service;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void setDao() throws Exception {

    }

    @Test
    public void getOrder() throws Exception {
        Order expected_1 = (Order) applicationContext.getBean("order_1");
        Order actual_1 = service.getOrder(expected_1.getKey());
        assertEquals(expected_1, actual_1);

        Order expected_2 = (Order) applicationContext.getBean("order_2");
        Order actual_2 = service.getOrder(expected_2.getKey());
        assertEquals("Fails to get order with multiple orderItems", expected_2, actual_2);
    }

    @Test
    public void saveOrder() throws Exception {

    }

    @Test
    public void findAll() throws Exception {

    }

    @Test
    public void buildOrder() throws Exception {

    }

}