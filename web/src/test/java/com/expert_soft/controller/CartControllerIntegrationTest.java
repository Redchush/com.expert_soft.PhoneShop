package com.expert_soft.controller;

import com.expert_soft.model.Cart;
import com.expert_soft.model.OrderItem;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
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
        "classpath:test-root-context.xml",
        "classpath:test-servlet-context.xml",
        "classpath:web_test-bean.xml"
})
@WebAppConfiguration
public class CartControllerIntegrationTest {

    private static final Logger logger = Logger.getLogger(ProductController.class);

    @Autowired private WebApplicationContext context;
    @Autowired private CartController controller;

    private MockMvc mockMvc;
    private Cart cart_1;

    @Before
    public void setup() {
        // Process mock annotations
        MockitoAnnotations.initMocks(this);
        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        Cart cart_1 = new Cart();
        OrderItem orderItem =
                context.getBean("orderItem_firstOrder_new", OrderItem.class);
        cart_1.putItem(orderItem);
        this.cart_1 = cart_1;
    }

    @Test
    public void cart() throws Exception {
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("cart", cart_1);

        MockHttpServletRequestBuilder requestBuilder  =
                MockMvcRequestBuilders.get("/cart")
                                      .sessionAttrs(sessionattr);
        mockMvc.perform(requestBuilder)
               .andExpect(status().isOk())
               .andExpect(model().attribute("cart", cart_1));




    }

}
