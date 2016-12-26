package com.expert_soft.persistence.impl;

import com.expert_soft.model.Phone;
import com.expert_soft.persistence.PhoneDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.lang.reflect.Field;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:persistence-context.xml",
        "classpath:test-dataSource-context.xml"
//        ,"classpath:test-transaction-context.xml"

})

@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class
        ,TransactionalTestExecutionListener.class
} )
@Transactional
public class PhoneDaoImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private PhoneDao dao;

    @Test
    public void setDataSource() throws Exception {
        DataSource dataSource = jdbcTemplate.getDataSource();
        assertEquals(dataSource, dataSource);
    }

    @Test
    public void getPhone() throws Exception {
        Phone actual = dao.getPhone(1l);
        Phone expected = (Phone) applicationContext.getBean("PhoneDaoImplTest_getPhone");
        assertEquals(expected, actual);
    }

    @Test
    public void savePhone() throws Exception {

    }

    @Test
    public void findAll() throws Exception {

    }

}