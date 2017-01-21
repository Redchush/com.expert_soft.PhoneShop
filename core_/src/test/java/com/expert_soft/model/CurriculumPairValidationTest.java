package com.expert_soft.model;


import com.expert_soft.model.excluded.CurriculumPair;
import com.expert_soft.model.util.TestValidationUtil;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.Validator;
import java.util.Properties;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:common_context.xml",
        "classpath:specific/validation_context.xml"
})
public class CurriculumPairValidationTest {


    private static final Logger LOGGER = Logger.getLogger(UserInfoValidationTest.class);

    @Autowired  ApplicationContext apCtx;
    @Autowired  Validator validator;
    @Autowired @Qualifier("valid_msg") Properties properties;
    @Autowired  TestValidationUtil util;

    private String phoneIdMinMsg;
    private String phoneIdNotNullMsg;
    private String quantMinMsg;
    private String quantMaxMsg;
    private String quantNotNullMsg;


    @Before
    public void setUp() throws Exception {
        phoneIdMinMsg = properties.getProperty("curriculumPair.phoneId.min");
        phoneIdNotNullMsg = properties.getProperty("curriculumPair.phoneId.notNull");

        quantMinMsg = properties.getProperty("curriculumPair.quantity.min");
        quantMaxMsg = properties.getProperty("curriculumPair.quantity.max");
        quantNotNullMsg = properties.getProperty("curriculumPair.quantity.notNull");
    }

    @Test
    public void validatePair() throws Exception {
        CurriculumPair negativeQuantity = new CurriculumPair(1L, -2L);
        util.executeOneAndExpectMessage(negativeQuantity, -2L, quantMinMsg);

        CurriculumPair largeQuantity = new CurriculumPair(1L, 11L);
        util.executeOneAndExpectMessage(largeQuantity, 11L, quantMaxMsg);

        CurriculumPair nullQuantity = new CurriculumPair(1L, null);
        util.executeOneAndExpectMessage(nullQuantity, null, quantNotNullMsg);

        CurriculumPair negativeKey= new CurriculumPair(-1L, 1L);
        util.executeOneAndExpectMessage(negativeKey, -1L, phoneIdMinMsg);

        CurriculumPair nullKey = new CurriculumPair(null, 1L);
        util.executeOneAndExpectMessage(nullKey, null, phoneIdNotNullMsg);

    }





}
