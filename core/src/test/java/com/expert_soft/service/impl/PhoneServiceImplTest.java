package com.expert_soft.service.impl;

import com.expert_soft.model.Phone;
import com.expert_soft.service.PhoneService;
import com.expert_soft.util.CountRowResponsible;
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

import javax.validation.ConstraintViolationException;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
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
    @Autowired ApplicationContext apCtx;
    @Autowired CountRowResponsible rowCounter;

    @Autowired @Qualifier("phone_id_1") Phone phone_id_1;
    @Autowired @Qualifier("phone_id_2") Phone phone_id_2;

    @Test
    public void findAll() throws Exception {}

    @Test
    public void getPhonesByArray() throws Exception {}

    @Test
    public void getPhonesByCollection() throws Exception {}

    @Test(expected = com.expert_soft.exception.service.NoSuchEntityException.class)
    public void getPhone() throws Exception {
        service.getPhone(100L);
    }

    @Test(expected = com.expert_soft.exception.service.NotUniqueEntityException.class)
    public void savePhone() throws Exception {
        service.savePhone(phone_id_1);
    }

    @Test
    public void saveInvalidPhone() throws Exception {
        phone_id_1.setKey(null);
        phone_id_1.setModel("myModel");
        service.savePhone(phone_id_1);
        assertTrue("Service reject phone with null key", true);
        char[] chars = new char[500];
        try {
            Arrays.fill(chars, 's');
            phone_id_1.setModel(new String(chars));
            service.savePhone(phone_id_1);
            assertTrue("Phone with invalid model was saved", false);
        } catch (ConstraintViolationException e){
            System.out.println(e.getConstraintViolations());
            System.out.println();
            assertEquals(e.getConstraintViolations().iterator().next()
                          .getInvalidValue(),
                    new String(chars));
        }
    }

    @Test(expected = ConstraintViolationException.class)
    public void saveNullPhone() throws Exception {
       service.savePhone(null);
    }

}