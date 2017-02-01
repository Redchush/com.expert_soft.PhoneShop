package com.expert_soft.controller;

import com.expert_soft.config.ApplicationConfig;
import com.expert_soft.model.CartItemsContainer;
import com.expert_soft.model.OrderItem;
import com.expert_soft.model.order.Cart;
import com.expert_soft.test_util.DataBuilder;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;

import static com.expert_soft.controller.ServletConstants.MSG_CODE;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class} )
@WebAppConfiguration
@ActiveProfiles("dev")
public class CartControllerIntegrationTest {

    private static final Logger logger = Logger.getLogger(ProductController.class);

    @Autowired private WebApplicationContext context;
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

    @Test
    public void updateCart_Valid() throws Exception {
        CartItemsContainer container = new CartItemsContainer(1);
        OrderItem item = container.getItems()[0];
        item.setQuantity(2);
        item.getPhone().setKey(1L);

        MockHttpServletRequestBuilder requestBuilder  =
                MockMvcRequestBuilders.post("/update_cart")
                                      .param("items[0].phone.key", "1")
                                      .param("items[0].quantity", "2")
                                      .sessionAttr("cart", cartWithOneItem);
        mockMvc.perform(requestBuilder)
               .andExpect(status().is(302))
               .andExpect(flash().attribute(MSG_CODE, ServletConstants.MsgCodes.SUCCESS_UPDATE));

    }

    @Test
    public void updateCart_Invalid() throws Exception {
        BindingResult result = mock(BindingResult.class);

        MockHttpServletRequestBuilder requestBuilder  =
                MockMvcRequestBuilders.post("/update_cart")
                                      .param("items[0].phone.key", "1")
                                      .param("items[0].quantity", "15")
                                      .sessionAttr("cart", cartWithOneItem);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

}
