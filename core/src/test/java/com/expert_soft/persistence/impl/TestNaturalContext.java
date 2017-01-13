package com.expert_soft.persistence.impl;


import com.expert_soft.model.Phone;
import com.expert_soft.persistence.OrderDao;
import com.expert_soft.persistence.PhoneDao;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:persistence-context.xml",
        "classpath:dataSource-context.xml",
        "classpath:test-data.xml",
})
@TestPropertySource(locations = "classpath:db.properties")
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class
})
@Transactional
public class TestNaturalContext {

    private static final Logger logger = Logger.getLogger(TestNaturalContext.class);

    @Autowired
    private PhoneDao dao;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void getOnePhone(){
        Phone phone = dao.getPhone(1L);
        assertNotNull(phone);
    }

    @Test
    public void isSchemaExist() throws SQLException {
        DataSource schema = (DataSource) applicationContext.getBean("schema");
        String url = schema.getConnection().getMetaData().getURL();
        logger.debug(url);

    }
}
