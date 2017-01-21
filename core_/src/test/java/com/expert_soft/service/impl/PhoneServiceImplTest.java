package com.expert_soft.service.impl;

import com.expert_soft.model.Phone;
import com.expert_soft.service.PhoneService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import util.CountRowResponsible;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:common_context.xml",
        "classpath:persistence-context.xml",
        "classpath:test-dataSource-context.xml",
        "classpath:core_test-bean.xml",
        "classpath:service-context.xml"
})
@TestPropertySource(locations =
        "classpath:config/applicationTest.properties")
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
})
public class PhoneServiceImplTest {

    private static final Logger logger = Logger.getLogger(CartServiceImplTest.class);

    @Autowired PhoneService service;
    @Autowired private ApplicationContext apCtx;
    @Autowired CountRowResponsible rowCounter;

    @Autowired @Qualifier("phone_id_1") Phone phone_id_1;
    @Autowired @Qualifier("phone_id_2") Phone phone_id_2;

    @Test
    public void findAll() throws Exception {

    }

    @Test
    public void getPhonesByArray() throws Exception {

    }

    @Test
    public void getPhonesByCollection() throws Exception {

    }

    @Test(expected = com.expert_soft.exception.service.NoSuchEntityException.class)
    public void getPhone() throws Exception {
        service.getPhone(100L);
    }

    @Test(expected = com.expert_soft.exception.service.NotUniqueEntityException.class)
    public void savePhone() throws Exception {
        service.savePhone(phone_id_1);
    }

}