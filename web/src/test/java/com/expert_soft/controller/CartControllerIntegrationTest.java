package com.expert_soft.controller;

import com.expert_soft.config.ApplicationConfiguration;
import com.expert_soft.model.Cart;
import com.expert_soft.model.CartItemsContainer;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.HashMap;

import static com.expert_soft.controller.ServletConstants.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfiguration.class, TestDataConfig.class} )
@WebAppConfiguration
public class CartControllerIntegrationTest {

    private static final Logger logger = Logger.getLogger(ProductController.class);

    @Autowired private WebApplicationContext context;
    @Autowired private CartController controller;

    private MockMvc mockMvc;
    private Cart cartWithOneItem;

    @Before
    public void setup() {
        // Process mock annotations
        MockitoAnnotations.initMocks(this);
        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        this.cartWithOneItem = context.getBean("cart_1_calculated", Cart.class);
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

    @Test
    public void changeQuantity() throws Exception {
        BindingResult result = mock(BindingResult.class);

        CartItemsContainer container = new CartItemsContainer(1);
        OrderItem item = container.getItems()[0];
        item.setQuantity(2);
        item.getPhone().setKey(1L);

        MockHttpServletRequestBuilder requestBuilder  =
                MockMvcRequestBuilders.post("/update_cart")
                                      .param("items[0].phone.key", "1")
                                      .param("items[0].quantity", "2")
//                                      .requestAttr(CART_ITEMS, container)
                                      .sessionAttr("cart", cartWithOneItem);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String errorMessage = mvcResult.getResponse().getContentAsString();
        System.out.println("error msg : " + errorMessage);
        logger.debug(errorMessage);
        mockMvc.perform(requestBuilder)
               .andExpect(status().isOk());



    }

}
