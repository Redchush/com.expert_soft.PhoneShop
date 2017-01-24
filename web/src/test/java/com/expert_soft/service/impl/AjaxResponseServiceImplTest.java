package com.expert_soft.service.impl;

import com.expert_soft.config.ApplicationConfiguration;
import com.expert_soft.controller.AjaxCartController;
import com.expert_soft.model.AjaxResponseCart;
import com.expert_soft.model.Cart;
import com.expert_soft.service.AjaxResponseService;
import com.expert_soft.util.JsonTestHelper;
import com.expert_soft.util.TestDataConfig;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfiguration.class, TestDataConfig.class} )
@WebAppConfiguration
public class AjaxResponseServiceImplTest {

    private static final Logger LOGGER = Logger.getLogger(AjaxResponseServiceImplTest.class);

    @Autowired AjaxResponseService service;
    @Autowired WebApplicationContext ctx;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders
                    .standaloneSetup(new AjaxCartController()).build();
    }

    @Test
    public void buildSuccess() throws Exception {
        Cart cart_1_calculated = ctx.getBean("cart_2_calculated", Cart.class);

        String testModel = service.buildAjaxSuccess(cart_1_calculated,"TestModel");
        AjaxResponseCart cart = JsonTestHelper.parseToObj(testModel, AjaxResponseCart.class);

        assertEquals(200, cart.getCode().intValue());
    }

    @Test
    public void buildFailToWrite() throws Exception {

    }

    @Test
    public void buildFail() throws Exception {
        String s = service.buildAjaxFail("{addMsg}", 400);

        AjaxResponseCart cart = JsonTestHelper.parseToObj(s, AjaxResponseCart.class);
        assertTrue("Exception raised during buildAjaxFail", true);
        assertEquals(true, cart.getPattern());
        assertEquals(400, cart.getCode().intValue());
    }

    @Test
    public void buildFailInvalidFormat() throws IOException {
        String s = service.buildAjaxFailInvalidFormat(422);

        AjaxResponseCart cart_actual = JsonTestHelper.parseToObj(s, AjaxResponseCart.class);
        String msgExpected = "Model of phone {model} cant be saved in cart." +
                "\nThe quantity of ordered phones must be positive integer number.";

        assertEquals(true, cart_actual.getPattern());
        assertEquals(422, cart_actual.getCode().intValue());
        assertEquals(msgExpected, cart_actual.getMsg());
    }

    @Test
    public void buildFailUnexpected() throws IOException {
        String s = service.buildAjaxFailUnexpected();
        AjaxResponseCart cart_actual = JsonTestHelper.parseToObj(s, AjaxResponseCart.class);
        assertEquals(500, cart_actual.getCode().intValue());
    }



}