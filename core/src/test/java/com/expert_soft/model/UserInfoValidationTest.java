package com.expert_soft.model;

import com.expert_soft.model.order.Order;
import com.expert_soft.util.DataBuilder;
import com.expert_soft.validator.group.G_Order;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static com.expert_soft.util.asserts.ValidationAsserts._assertOneInvalidField;

public class UserInfoValidationTest {

    private UserInfo userInfo;

    @Before
    public void setUp() throws Exception {
        Order order = DataBuilder.Order_1.getOrder_DB();
        userInfo = order.getUserInfo();
    }

    @Test
    public void setFirstName() throws Exception {
        String number = "11111";
        String expNumberMsg = "The first name must contains only english characters and '.' sign";
        userInfo.setFirstName(number);
        _assertOneInvalidField(userInfo, number, expNumberMsg, G_Order.Info.class);

        String oneSign = "z";
        String expectedMsg = "The first name must be between 3 and 100 chars long";
        userInfo.setFirstName(oneSign);
        _assertOneInvalidField(userInfo, oneSign, expectedMsg, G_Order.Info.class);

    }

    @Test
    public void setLastName() throws Exception {
        String number = "11111";
        String expNumberMsg = "The last name must contains only english characters and '.' sign";
        userInfo.setLastName(number);
        _assertOneInvalidField(userInfo, number, expNumberMsg,  G_Order.Info.class);


        String oneSign = "z";
        String expectedMsg = "The last name must be between 3 and 100 chars long";
        userInfo.setLastName(oneSign);
        _assertOneInvalidField(userInfo, oneSign, expectedMsg,  G_Order.Info.class);
    }

    @Test
    public void setDeliveryAddress() throws Exception {
        String minorAddr = "11111";
        userInfo.setLastName(minorAddr);
        _assertOneInvalidField(userInfo, minorAddr,  G_Order.Info.class);
    }

    @Test
    public void setContactPhoneNo() throws Exception {
        String characterNumber = "invalid number";
        userInfo.setContactPhoneNo(characterNumber);
        _assertOneInvalidField(userInfo, characterNumber,  G_Order.Info.class);
    }

    @Test
    public void setAdditionalInfo() throws Exception {
        char[] chars = new char[501];
        Arrays.fill(chars, 's');
        String largeInfo = new String(chars);
        userInfo.setAdditionalInfo(largeInfo);
        _assertOneInvalidField(userInfo, largeInfo,  G_Order.Info.class);
    }
}