package com.expert_soft.service.impl;

import com.expert_soft.model.OrderItem;
import com.expert_soft.model.Phone;
import com.expert_soft.model.calculator.OrderCalculatorImpl;
import com.expert_soft.model.order.Cart;
import com.expert_soft.util.DataBuilder;
import com.expert_soft.util.asserts.ValidationAsserts;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:context/core_root-context.xml"
})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
})
@ActiveProfiles("dev")
public class OrderServiceImplIntValidationTest {

    private static OrderServiceImpl service;

    @BeforeClass
    public static void beforeClass(){
        service = new OrderServiceImpl();
        service.setCalculator(Mockito.mock(OrderCalculatorImpl.class));
        service.setValidator(ValidationAsserts.getDefaultValidator());
    }

    @Test(expected = ConstraintViolationException.class)
    public void addToCart_To_Much() throws Exception {
        Phone phone = new Phone();
        phone.setKey(1L);
        service.addToCart(new Cart(), phone, 12);
    }

    @Test
    public void createOrderItemValidationMsg() throws Exception {
        Properties properties = DataBuilder.Validation.getValidationMsgs();
        try {
            service.addToCart(new Cart(),DataBuilder.getRandomPhone(), 12);
            assertFalse("validation don't work", true);
        } catch (ConstraintViolationException e){
            String expectedMsg = properties.getProperty("orderItem.quantity.max");
            ConstraintViolation<?> next = e.getConstraintViolations().iterator().next();
            assertEquals(12, next.getInvalidValue());
            assertEquals(expectedMsg, next.getMessage());
        }
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
