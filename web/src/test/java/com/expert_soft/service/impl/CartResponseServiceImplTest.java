package com.expert_soft.service.impl;

import com.expert_soft.controller.cart.AjaxAddToCartController;
import com.expert_soft.form.AjaxResponseCart;
import com.expert_soft.model.Phone;
import com.expert_soft.model.order.Cart;
import com.expert_soft.model.order.OrderItem;
import com.expert_soft.service.CartResponseService;
import com.expert_soft.test_util.DataBuilder;
import com.expert_soft.test_util.JsonTestHelper;
import com.expert_soft.test_util.TestConstants;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        TestConstants.SERVLET_CONTEXT})
@WebAppConfiguration
@ActiveProfiles("test")
public class CartResponseServiceImplTest {

    private static final Logger LOGGER = Logger.getLogger(CartResponseServiceImplTest.class);

    @Autowired private CartResponseService service;
    @Autowired private MessageSource messageSource;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new AjaxAddToCartController()).build();
    }

    @Test
    public void buildSuccess() throws Exception {
        Cart cart_1_calculated = DataBuilder.Carts.byOrder_2();
        String testModel = service.buildJsonSuccess(cart_1_calculated,"TestModel");
        AjaxResponseCart cart = JsonTestHelper.parseToObj(testModel, AjaxResponseCart.class);
        assertEquals(200, cart.getCode().intValue());
    }

    @Test
    public void buildFailToWrite() throws Exception {

    }

    @Test
    public void buildFail() throws Exception {
        OrderItem item = new OrderItem(new Phone(1L), 9);
        String s = service.buildFail(null, item);
        LOGGER.debug(s);

    }

    @Test
    public void buildFailAjax() throws Exception {
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
        String msgExpected = "Model of phone {form} cant be saved in cart." +
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