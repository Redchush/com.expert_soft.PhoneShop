package com.expert_soft.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:core_test-bean.xml",
        "classpath:common_context.xml",
        "classpath:specific/validation_context.xml"

})
@TestPropertySource(locations = "classpath:config/applicationTest.properties")
public class OrderValidationTest {


    @Test
    public void getUserInfo() throws Exception {

    }

    @Test
    public void setUserInfo() throws Exception {

    }

}