package com.expert_soft.controller;


import com.expert_soft.config.ApplicationConfig;
import com.expert_soft.model.order.Cart;
import com.expert_soft.test_util.DataBuilder;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.expert_soft.controller.ServletConstants.*;
import static com.expert_soft.test_util.asserts.ModelAsserts._assertEquals;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class} )
@WebAppConfiguration
@ActiveProfiles("dev")
public class AjaxCartControllerIntegrationTest {

    private static final Logger logger = Logger.getLogger(ProductController.class);

    @Autowired private WebApplicationContext context;
    @Autowired private AjaxCartController controller;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void addToCart() throws Exception {
        Cart actual = new Cart();
        Cart expected = DataBuilder.Carts.byOrder_1();
        MockHttpServletRequestBuilder requestBuilder  =
                MockMvcRequestBuilders.get("/add_to_cart")
                                      .param(PHONE_ID_TO_ADD, "1")
                                      .param(QUANTITY_PARAM, "1")
                                      .sessionAttr(CART_ATTR, actual)
                                      .accept(MediaType.APPLICATION_JSON_VALUE);
        mockMvc.perform(requestBuilder)
               .andExpect(status().isOk())
               .andExpect(content()
                       .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andReturn();
        _assertEquals(expected, actual);
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
