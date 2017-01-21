package com.expert_soft.config.impl;

import com.expert_soft.config.AppConfigProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:common_context.xml",
})
public class AppConfigPropertiesImplTest {

    @Autowired
    AppConfigProperties properties;

    @Test
    public void getProperty() throws Exception {
        String property = properties.getProperty("delivery.price");
        assertEquals("5", property);
    }

}