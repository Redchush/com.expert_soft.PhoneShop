package com.expert_soft.model;

import com.expert_soft.model.util.TestValidationUtil;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.Validator;
import java.util.Iterator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:test-data.xml",
        "classpath:specific/validation_context.xml"
})

public class OrderItemValidationTest {

    private static final Logger LOGGER = Logger.getLogger(UserInfoValidationTest.class);

    @Autowired
    private ApplicationContext apCtx;

    @Autowired
    private Validator validator;

    @Autowired
    private TestValidationUtil util;

    private Order order;
    private OrderItem firstItem;
    private OrderItem secondItem;


    @Before
    public void setUp() throws Exception {
        order = (Order) apCtx.getBean("order_2");
        Iterator<OrderItem> iterator = order.getOrderItems().iterator();
        firstItem = iterator.next();
        secondItem = iterator.next();


    }
    @Test
    public void setQuantity() throws Exception {
        Long largeValue = 100L;
        firstItem.setQuantity(largeValue);
        util.executeOneInvalidField(firstItem, largeValue);

        Long negativeValue = -1L;
        firstItem.setQuantity(negativeValue);
        util.executeOneInvalidField(firstItem, negativeValue);
    }

}