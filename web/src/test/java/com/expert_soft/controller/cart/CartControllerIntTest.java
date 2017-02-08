package com.expert_soft.controller.cart;

import com.expert_soft.model.order.Cart;
import com.expert_soft.test_util.DataBuilder;
import com.expert_soft.test_util.TestConstants;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        TestConstants.ROOT_CONTEXT,
        TestConstants.SERVLET_CONTEXT})
@WebAppConfiguration
@ActiveProfiles("test")
public class CartControllerIntTest {

    private static final Logger logger = Logger.getLogger(CartControllerIntTest.class);

    @Autowired
    private WebApplicationContext context;
    @Autowired private CartController controller;

    private MockMvc mockMvc;
    private Cart cartWithOneItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        this.cartWithOneItem = DataBuilder.Carts.byOrder_1_clean();
    }

    @Test
    public void cart() throws Exception {
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("cart", cartWithOneItem);

        MockHttpServletRequestBuilder requestBuilder  =
                MockMvcRequestBuilders.get("/cart")
                                      .sessionAttrs(sessionattr);
        mockMvc.perform(requestBuilder)
               .andExpect(status().isOk())
               .andExpect(model().attribute("cart", cartWithOneItem));

    }
}
