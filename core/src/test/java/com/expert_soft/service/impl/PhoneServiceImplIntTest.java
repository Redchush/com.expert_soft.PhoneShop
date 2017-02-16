package com.expert_soft.service.impl;

import com.expert_soft.model.Phone;
import com.expert_soft.service.PhoneService;
import com.expert_soft.test_util.Context;
import com.expert_soft.test_util.DataBuilder;
import com.expert_soft.test_util.db.DbInfo;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
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

import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        Context.ROOT_WITH_CART
})
@TestPropertySource(locations =
        "classpath:config/applicationTest.properties")

@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
})
@ActiveProfiles("test")
public class PhoneServiceImplIntTest {

    private static final Logger logger = Logger.getLogger(CartServiceImplTest.class);

    @Autowired private PhoneService service;
    @Autowired private DataSource dataSource;

    private DbInfo rowCounter;
    private Phone phone_id_1;

    @Before
    public void setUp() throws Exception {
        rowCounter = new DbInfo(new JdbcTemplate(dataSource));
        phone_id_1 = DataBuilder.getPhoneId_1();
    }

    @Test
    public void findAll() throws Exception {}

    @Test
    public void getPhonesByArray() throws Exception {}

    @Test
    public void getPhonesByCollection() throws Exception {}

    @Test
    public void getPhone() throws Exception {
        Phone phone = service.getPhone(100L);
        assertNull(phone);
    }

    @Test
    public void savePhone_Invalid() throws Exception {
        Number number = service.savePhone(phone_id_1);
        assertNull(number);
    }

    @Test
    @Ignore
    public void saveInvalidPhone() throws Exception {
        //validation must be on controller layer
        phone_id_1.setKey(null);
        phone_id_1.setModel("myModel");
        service.savePhone(phone_id_1);
        assertTrue("Service reject phone with null key", true);
        char[] chars = new char[500];
        try {
            Arrays.fill(chars, 's');
            phone_id_1.setModel(new String(chars));
            service.savePhone(phone_id_1);
            assertTrue("Phone with invalid form was saved", false);
        } catch (ConstraintViolationException e){
            assertEquals(e.getConstraintViolations().iterator().next()
                          .getInvalidValue(),
                    new String(chars));
        }
    }

    @Test(expected = NullPointerException.class)
    public void saveNullPhone() throws Exception {
       service.savePhone(null);
    }

}