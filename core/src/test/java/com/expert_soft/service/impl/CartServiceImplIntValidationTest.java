package com.expert_soft.service.impl;

import com.expert_soft.model.Phone;
import com.expert_soft.model.calculator.impl.OrderCalculatorImpl;
import com.expert_soft.model.order.Cart;
import com.expert_soft.model.order.OrderItem;
import com.expert_soft.model.result.ValidationResult;
import com.expert_soft.test_util.DataBuilder;
import com.expert_soft.test_util.asserts.ValidationAsserts;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import javax.validation.ConstraintViolation;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;



public class CartServiceImplIntValidationTest {

    private static CartServiceImpl service;


    @BeforeClass
    public static void beforeClass(){
        service = new CartServiceImpl();
        service.setCalculator(Mockito.mock(OrderCalculatorImpl.class));
        service.setValidator(ValidationAsserts.getDefaultValidator());
    }

    @Before
    public void setUp(){
        service.setCart(new Cart());
    }

    @Test
    public void addToCart_To_Much() throws Exception {
        Phone phone = new Phone();
        phone.setKey(1L);
        ValidationResult<OrderItem> validationResult = service.addToCart(phone, 12);
        assertFalse(validationResult.isSuccess());
    }

    @Test
    public void addToCart_To_Much_SUM() throws Exception {
        Phone phone = new Phone();
        phone.setKey(1L);

        Cart actual = service.getCart();
        actual.addItem(new OrderItem(phone, 9));

        ValidationResult<OrderItem> validationResult = service.addToCart(phone, 3);
        assertFalse(validationResult.isSuccess());
    }

    @Test
    public void createOrderItemValidationMsg() throws Exception {
        Properties properties = DataBuilder.Validation.getValidationMsgs();
        ValidationResult<OrderItem> result =
                    service.addToCart(DataBuilder.getRandomPhone(), 12);
        String expectedMsg = properties.getProperty("orderItem.quantity.max");
        ConstraintViolation<?> next = result.getViolations().iterator().next();
        assertEquals(12, next.getInvalidValue());
        assertEquals(expectedMsg, next.getMessage());

    }

    @Test
    public void addToCart_TEN(){
        Cart cart = service.getCart();
        Phone phone = new Phone();
        phone.setKey(1L);

        service.addToCart(phone, 4);
        service.addToCart(phone, 6);

        assertEquals(10, cart.getItem(1L).getQuantity().intValue());

    }




}
