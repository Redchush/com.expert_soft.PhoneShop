package com.expert_soft.controller;


import com.expert_soft.config.ApplicationConfiguration;
import com.expert_soft.model.Cart;
import com.expert_soft.model.OrderItem;
import com.expert_soft.util.TestDataConfig;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;

import static com.expert_soft.model.ServletConstants.PHONE_ID_TO_ADD;
import static com.expert_soft.model.ServletConstants.QUANTITY_PARAM;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfiguration.class, TestDataConfig.class} )
@WebAppConfiguration
public class AjaxCartControllerIntegrationTest {

    private static final Logger logger = Logger.getLogger(ProductController.class);

    @Autowired
    private WebApplicationContext context;

    @Autowired private AjaxCartController controller;
    private MockMvc mockMvc;
    private Cart cart_1;

    @Before
    public void setup() {
        // Process mock annotations
        MockitoAnnotations.initMocks(this);
        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        Cart cart_1 = context.getBean("cart_1_calculated", Cart.class);
        OrderItem orderItem =
                context.getBean("orderItem_firstOrder_new", OrderItem.class);
        cart_1.putItem(orderItem);
        this.cart_1 = cart_1;
    }

    @Test
    public void addToCart() throws Exception {
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("cart", new Cart());

        MockHttpServletRequestBuilder requestBuilder  =
                MockMvcRequestBuilders.get("/add_to_cart")
                                      .param(PHONE_ID_TO_ADD, "1")
                                      .param(QUANTITY_PARAM, "1")
                                      .sessionAttrs(sessionattr)
                                      .accept(MediaType.APPLICATION_JSON_VALUE);
        mockMvc.perform(requestBuilder)
               .andExpect(status().isOk())
               .andExpect(content()
                       .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andReturn();
        assertEquals(sessionattr.get("cart"), cart_1);
    }


    @Test
    public void ajaxItemViolation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/add_to_cart")
                                     .param(PHONE_ID_TO_ADD, "-1")
                                     .param(QUANTITY_PARAM, "5")
                                     .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isUnprocessableEntity())
               .andExpect(content()
                       .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.get("/add_to_cart")
                                      .param(PHONE_ID_TO_ADD, "1")
                                      .param(QUANTITY_PARAM, "20")
                                      .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isUnprocessableEntity())
               .andExpect(content()
                       .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

}
