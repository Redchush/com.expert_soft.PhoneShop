package com.expert_soft.controller.cart;


import com.expert_soft.model.order.Cart;
import com.expert_soft.test_util.DataBuilder;
import com.expert_soft.test_util.TestConstants;
import org.apache.log4j.Logger;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.expert_soft.controller.ServletConstants.PHONE_ID_TO_ADD;
import static com.expert_soft.controller.ServletConstants.QUANTITY_PARAM;
import static com.expert_soft.test_util.asserts.ModelAsserts._assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        TestConstants.ROOT_CONTEXT,
        TestConstants.SERVLET_CONTEXT})
@WebAppConfiguration
@ActiveProfiles("test")
public class AjaxAddToCartControllerIntTest {

    private static final Logger logger = Logger.getLogger(AjaxAddToCartControllerIntTest.class);

    @Autowired private AjaxAddToCartController controller;
    @Autowired MockHttpSession session;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        session.setAttribute("cart", new Cart());
    }

    @Test
    public void addToCart() throws Exception {

        Cart expected = DataBuilder.Carts.byOrder_1();
        MockHttpServletRequestBuilder requestBuilder  =
                MockMvcRequestBuilders.get("/add_to_cart")
                                      .param(PHONE_ID_TO_ADD, "1")
                                      .param(QUANTITY_PARAM, "1")
                                      .session(session)
                                      .accept(MediaType.APPLICATION_JSON_VALUE);
        mockMvc.perform(requestBuilder)
               .andExpect(status().isOk())
               .andExpect(content()
                       .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andReturn();
        Cart actual = (Cart) session.getAttribute("cart");
        _assertEquals(expected, actual);
    }

    @Test
    public void addToCart_cartExist() throws Exception {
        MockHttpServletRequestBuilder requestBuilder  =
                MockMvcRequestBuilders.get("/add_to_cart")
                                      .param(PHONE_ID_TO_ADD, "1")
                                      .param(QUANTITY_PARAM, "1")
                                      .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
               .andExpect(status().isOk())
               .andExpect(request()
               .sessionAttribute("cart", Matchers.notNullValue()));
    }

    @Test
    public void ajaxItemViolation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/add_to_cart")
                                     .param(PHONE_ID_TO_ADD, "-1")
                                     .param(QUANTITY_PARAM, "5")
                                     .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isUnprocessableEntity())
               .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.get("/add_to_cart")
                                      .param(PHONE_ID_TO_ADD, "1")
                                      .param(QUANTITY_PARAM, "20")
                                      .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isUnprocessableEntity());

    }

}
