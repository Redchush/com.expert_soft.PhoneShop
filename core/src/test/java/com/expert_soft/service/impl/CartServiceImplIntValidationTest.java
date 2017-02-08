package com.expert_soft.service.impl;

import com.expert_soft.model.OrderItem;
import com.expert_soft.model.Phone;
import com.expert_soft.model.calculator.OrderCalculatorImpl;
import com.expert_soft.model.order.Cart;
import com.expert_soft.model.result.ValidationResult;
import com.expert_soft.test_util.DataBuilder;
import com.expert_soft.test_util.asserts.ValidationAsserts;
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

    @Test
    public void addToCart_To_Much() throws Exception {
        Phone phone = new Phone();
        phone.setKey(1L);
        ValidationResult<OrderItem> validationResult = service.addToCart(new Cart(), phone, 12);
        assertFalse(validationResult.isSuccess());
    }

    @Test
    public void addToCart_To_Much_SUM() throws Exception {
        Cart emptyCart = new Cart();
        Phone phone = new Phone();
        phone.setKey(1L);
        emptyCart.addItem(new OrderItem(phone, 9));
        ValidationResult<OrderItem> validationResult = service.addToCart(emptyCart, phone, 3);
        assertFalse(validationResult.isSuccess());
    }

    @Test
    public void createOrderItemValidationMsg() throws Exception {
        Properties properties = DataBuilder.Validation.getValidationMsgs();
        ValidationResult<OrderItem> result =
                    service.addToCart(new Cart(), DataBuilder.getRandomPhone(), 12);
        String expectedMsg = properties.getProperty("orderItem.quantity.max");
        ConstraintViolation<?> next = result.getViolations().iterator().next();
        assertEquals(12, next.getInvalidValue());
        assertEquals(expectedMsg, next.getMessage());

    }

    @Test
    public void addToCart_TEN(){
        Cart emptyCart = new Cart();
        Phone phone = new Phone();
        phone.setKey(1L);
        service.addToCart(emptyCart, phone, 4);
        service.addToCart(emptyCart, phone, 6);
        assertEquals(10, emptyCart.getItem(1L).getQuantity().intValue());

    }




}
