package com.expert_soft.service.impl;

import com.expert_soft.model.Phone;
import com.expert_soft.service.PhoneService;
import com.expert_soft.util.db.CountRowResponsible;
import com.expert_soft.util.DataBuilder;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.sql.DataSource;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:context/core_root-context.xml"
})
@TestPropertySource(locations =
        "classpath:config/applicationTest.properties")

@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
})
@ActiveProfiles("dev")
public class PhoneServiceImplIntTest {

    private static final Logger logger = Logger.getLogger(CartServiceImplTest.class);

    @Autowired PhoneService service;
    @Autowired DataSource dataSource;

    private CountRowResponsible rowCounter;
    private Phone phone_id_1;

    @Before
    public void setUp() throws Exception {
        rowCounter = new CountRowResponsible(new JdbcTemplate(dataSource));
        phone_id_1 = DataBuilder.getPhoneId_1();
    }

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