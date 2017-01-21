package com.expert_soft.model;

import com.expert_soft.model.util.TestValidationUtil;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.Validator;
import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:core_test-bean.xml",
        "classpath:specific/validation_context.xml"
})

public class UserInfoValidationTest {
    private static final Logger LOGGER = Logger.getLogger(UserInfoValidationTest.class);

    @Autowired  ApplicationContext apCtx;
    @Autowired  Validator validator;
    @Autowired  TestValidationUtil util;

    private Order order;
    private UserInfo userInfo;

    @Before
    public void setUp() throws Exception {
        order = (Order) apCtx.getBean("order_2_db");
        userInfo = order.getUserInfo();
    }



    @Test
    public void setFirstName() throws Exception {
        String number = "11111";
        String expNumberMsg = "The first name must contains only english characters and '.' sign";
        userInfo.setFirstName(number);
        util.executeOneAndExpectMessage(userInfo, number, expNumberMsg);

        String oneSign = "z";
        String expectedMsg = "The first name must be between 3 and 100 chars long";
        userInfo.setFirstName(oneSign);
        util.executeOneAndExpectMessage(userInfo, oneSign, expectedMsg);

    }

    @Test
    public void setLastName() throws Exception {
        String number = "11111";
        String expNumberMsg = "The last name must contains only english characters and '.' sign";
        userInfo.setLastName(number);
        util.executeOneAndExpectMessage(userInfo, number, expNumberMsg);


        String oneSign = "z";
        String expectedMsg = "The last name must be between 3 and 100 chars long";
        userInfo.setLastName(oneSign);
        util.executeOneAndExpectMessage(userInfo, oneSign, expectedMsg);
    }

    @Test
    public void setDeliveryAddress() throws Exception {
        String minorAddr = "11111";
        userInfo.setLastName(minorAddr);
        util.executeOneInvalidField(userInfo, minorAddr);
    }

    @Test
    public void setContactPhoneNo() throws Exception {
        String characterNumber = "invalid number";
        userInfo.setContactPhoneNo(characterNumber);
        util.executeOneInvalidField(userInfo, characterNumber);
    }

    @Test
    public void setAdditionalInfo() throws Exception {
        char[] chars = new char[501];
        Arrays.fill(chars, 's');
        String largeInfo = new String(chars);
        userInfo.setAdditionalInfo(largeInfo);
        util.executeOneInvalidField(userInfo, largeInfo);
    }

    @Test
    public void getFirstName() throws Exception {
        /*not implemented*/
    }
    @Test
    public void getAdditionalInfo() throws Exception {
       /*not implemented*/
    }
    @Test
    public void getLastName() throws Exception {
  /*not implemented*/
    }

    @Test
    public void getDeliveryAddress() throws Exception {
        /*not implemented*/
    }

    @Test
    public void getContactPhoneNo() throws Exception {
         /*not implemented*/
    }



}