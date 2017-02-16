package com.expert_soft.model.order;

import com.expert_soft.model.Phone;
import com.expert_soft.test_util.DataBuilder;
import com.expert_soft.validator.group.G_Cart;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Properties;

import static com.expert_soft.test_util.asserts.ValidationAsserts._assertOneInvalidField;


public class CartValidationTest {

    private static Properties properties;

    @BeforeClass
    public static void setUp() throws Exception {
        properties = DataBuilder.Validation.getValidationMsgs();
    }

    @Test
    public void validateItem(){
        Phone phone = new Phone(1L);
        OrderItem testItem = new OrderItem(phone, 12);
        Cart cart = new Cart();
        cart.addItem(testItem);
        String o = properties.getProperty("orderItem.quantity.max");
        _assertOneInvalidField(cart, 12, o, G_Cart.Item.class);
    }
}