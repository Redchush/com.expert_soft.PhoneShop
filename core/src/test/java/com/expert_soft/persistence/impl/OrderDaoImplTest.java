package com.expert_soft.persistence.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:persistence-context.xml",
        "classpath:test-dataSource-context.xml"
//        ,"classpath:test-transaction-context.xml"

})

@TestExecutionListeners( {
        DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class })
@Transactional
public class OrderDaoImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PhoneDaoImpl dao;

    @Test
    public void setDataSource() throws Exception {

    }

    @Test
    public void getOrder() throws Exception {

    }

    @Test
    public void saveOrder() throws Exception {

    }

    @Test
    public void findAll() throws Exception {

    }

}