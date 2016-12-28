package com.expert_soft.persistence.impl;

import com.expert_soft.model.Order;
import com.expert_soft.model.Phone;
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

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:persistence-context.xml",
        "classpath:test-dataSource-context.xml",
        "classpath:dataSource-context.xml"
//        ,"classpath:test-transaction-context.xml"

})

@TestExecutionListeners( {
        DependencyInjectionTestExecutionListener.class,
//        TransactionalTestExecutionListener.class
})
//@Transactional
public class OrderDaoImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private OrderDaoImpl dao;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ApplicationContext applicationContext;

    private static final int sizeExpected = 12;

    @Test
    public void setDataSource() throws Exception {
        DataSource dataSource = jdbcTemplate.getDataSource();
        assertEquals(dataSource, dataSource);
    }

    @Test
    public void getOrder() throws Exception {
        Order actual = dao.getOrder(1l);
        Order expected = (Order) applicationContext.getBean("orderDaoImplTest_getOrder");
        assertEquals(expected, actual);
    }

    @Test
    public void saveOrder() throws Exception {
        Long keyExpected = sizeExpected + 1L;
        Order expected = (Order) applicationContext.getBean("orderDaoImplTest_getOrder");
        expected.setDeliveryAddress("new adress");
        dao.saveOrder(expected);

        Order order = dao.getOrder(keyExpected);
        assertTrue("Order not saved at all",true);
        assertEquals("Order not saved with correct id expected",
                     keyExpected, order.getKey());

    }

    @Test
    public void findAll() throws Exception {
        List<Order> all = dao.findAll();
        assertEquals(sizeExpected, all.size());
    }

}