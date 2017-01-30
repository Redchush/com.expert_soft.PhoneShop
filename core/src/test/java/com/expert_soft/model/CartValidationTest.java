package com.expert_soft.model;

import com.expert_soft.model.util.TestValidationUtil;
import com.expert_soft.validator.group.G_Cart;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.Validator;
import java.util.Properties;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:core_test-bean.xml",
        "classpath:common_context.xml",
        "classpath:specific/validation_context.xml"

})
@TestPropertySource(locations = "classpath:config/applicationTest.properties")
public class CartValidationTest {

    private static final Logger LOGGER = Logger.getLogger(UserInfoValidationTest.class);

    @Autowired private ApplicationContext apCtx;
    @Autowired private Validator validator;
    @Autowired private TestValidationUtil util;
    @Autowired @Qualifier("valid_msg") Properties properties;


    @Autowired @Qualifier("orderItem_secondOrder_1_new") OrderItem firstItem;
    @Autowired @Qualifier("orderItem_secondOrder_2_new") OrderItem secondItem;

    @Test
    public void validateItem(){
        firstItem.setQuantity(12);
        Cart cart = new Cart();
        cart.putItem(firstItem);
        String o = properties.getProperty("orderItem.quantity.max");
        util.executeOneInvalidField(cart, 12, o, G_Cart.Item.class);
    }

    @Test
    public void putItem() throws Exception {

    }

    @Test
    public void putItem1() throws Exception {

    }

}