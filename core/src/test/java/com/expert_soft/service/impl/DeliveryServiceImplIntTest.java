package com.expert_soft.service.impl;

import com.expert_soft.service.DeliveryService;
import com.expert_soft.test_util.Context;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
       Context.ROOT_WITH_CART
})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
})
@ActiveProfiles("test")
public class DeliveryServiceImplIntTest {

    @Autowired private DeliveryService service;

    @Test
    public void getDeliveryPrice() throws Exception {
        BigDecimal property = service.getDeliveryPrice();
        assertEquals(new BigDecimal("5.00"), property);
    }

}