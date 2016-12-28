package com.expert_soft.persistence.impl.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.junit.Assert.*;


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

public class CollectorsTest {


}