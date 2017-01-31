package com.expert_soft.service.impl;

import com.expert_soft.service.DeliveryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:context/core_root-context.xml"
})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
})
@ActiveProfiles("dev")
public class DeliveryServiceImplIntTest {

    @Autowired private DeliveryService service;

    @Test
    public void getDeliveryPrice() throws Exception {
        BigDecimal property = service.getDeliveryPrice();
        assertEquals(new BigDecimal("5"), property);
    }

}