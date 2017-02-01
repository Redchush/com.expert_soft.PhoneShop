package com.expert_soft.model;


import com.expert_soft.test_util.DataBuilder;
import com.expert_soft.validator.group.G_Cart;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Properties;

import static com.expert_soft.test_util.asserts.ValidationAsserts._assertOneInvalidField;

public class OrderItemValidationTest {

    private static final Logger LOGGER = Logger.getLogger(UserInfoValidationTest.class);
    public static Properties properties;

    @BeforeClass
    public static void setUpClass() throws Exception {
        properties = DataBuilder.Validation.getValidationMsgs();
    }

    @Test
    public void setQuantity() throws Exception {
        OrderItem item = new OrderItem(new Phone(1L), null);
        Integer largeValue = 100;
        item.setQuantity(largeValue);
        _assertOneInvalidField(item, largeValue, G_Cart.Item.class);

        Integer negativeValue = -1;
        item.setQuantity(negativeValue);
        _assertOneInvalidField(item, negativeValue, G_Cart.Item.class);
    }

    @Test
    public void validatePhone(){
        String msg = properties.getProperty("common.key");
        OrderItem item = new OrderItem(new Phone(), 1);
        _assertOneInvalidField(item, null, msg, G_Cart.Item.class);
    }
}