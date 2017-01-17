package com.expert_soft.service.impl;

import com.expert_soft.model.Order;
import com.expert_soft.service.CartService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;


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
public class CartServiceImplTest {

    private static final Logger logger = Logger.getLogger(CartServiceImplTest.class);

    @Autowired
    private CartService service;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    @Qualifier("order_1")
    private Order order;


    @Test
    public void addToCart() throws Exception {

    }

    @Test
    public void addToCart1() throws Exception {

    }

    @Test
    public void deleteFromCart() throws Exception {

    }

    @Test
    public void deleteFromCart1() throws Exception {

    }

    @Test
    public void calculateAndSetSubtotal() throws Exception {
        logger.debug(order);
    }

    @Test
    public void calculateAndSetSubtotal1() throws Exception {

    }

}