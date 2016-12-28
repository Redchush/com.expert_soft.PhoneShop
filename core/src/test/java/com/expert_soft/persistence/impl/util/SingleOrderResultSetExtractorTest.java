package com.expert_soft.persistence.impl.util;

import com.expert_soft.model.Order;
import com.expert_soft.model.Phone;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import static org.hamcrest.Matchers.contains;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.*;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:persistence-context.xml",
        "classpath:test-dataSource-context.xml",
        "classpath:dataSource-context.xml"
})

@TestExecutionListeners( {
        DependencyInjectionTestExecutionListener.class,

})


public class SingleOrderResultSetExtractorTest {

    @Autowired
    private ApplicationContext applicationContext;


    @Test
    public void setPhoneRowMapper() throws Exception {

        ResultSetExtractor expected_1 =
                (ResultSetExtractor)applicationContext.getBean("single_order_extractor");

        RowMapper expected_2 =
                (RowMapper) applicationContext.getBean("phone_mapper");

        RowMapper expected_3 =
                (RowMapper) applicationContext.getBean("order_mapper");

        Class<? extends ResultSetExtractor> aClass = expected_1.getClass();

        Field phoneRowMapper = aClass.getDeclaredField("phoneRowMapper");
        phoneRowMapper.setAccessible(true);
        Object object = phoneRowMapper.get(expected_1);
        assertNotNull(object);
        assertEquals(object, expected_2);


        List<Object> dependentMappers = Arrays.stream(aClass.getDeclaredFields())
                                                 .map(s -> {
                                                     try {
                                                         s.setAccessible(true);
                                                         Object object1 = s.get(expected_1);
                                                         return object1;
                                                     } catch (IllegalAccessException e) {
                                                         System.out.println("Not accessible");
                                                         return null;
                                                     }
                                                 }).collect(java.util.stream.Collectors.toList());

        List<RowMapper> mappers = Arrays.asList(expected_2, expected_3);

        boolean b = dependentMappers.containsAll(mappers);
//        assertThat(dependentMappers, containsInAnyOrder(mappers));

        assertTrue("All dependencies set correctly", b);



    }

}