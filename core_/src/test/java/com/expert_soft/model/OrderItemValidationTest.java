package com.expert_soft.model;

import com.expert_soft.model.util.TestValidationUtil;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:core_test-bean.xml",
        "classpath:common_context.xml",
        "classpath:specific/validation_context.xml"

})
@TestPropertySource(locations = "classpath:config/applicationTest.properties")
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
        order = (Order) apCtx.getBean("order_2_db");
        Iterator<OrderItem> iterator = order.getOrderItems().iterator();
        firstItem = iterator.next();
        secondItem = iterator.next();
    }

    @Test
    public void isPricePropertyExist(){
        String priceProperty = apCtx.getEnvironment().getProperty("delivery.price");
        assertEquals("Test can't find property file", "5", priceProperty);
    }

    @Test
    public void setQuantity() throws Exception {
        Integer largeValue = 100;
        firstItem.setQuantity(largeValue);
        util.executeOneInvalidField(firstItem, largeValue);

        Integer negativeValue = -1;
        firstItem.setQuantity(negativeValue);
        util.executeOneInvalidField(firstItem, negativeValue);
    }

    @Test
    public void getDeliveryPrice() throws Exception {
        Order order_new = apCtx.getBean("order_new", Order.class);
        BigDecimal deliveryPrice = order_new.getDeliveryPrice();
        assertNotNull(deliveryPrice);
    }

}