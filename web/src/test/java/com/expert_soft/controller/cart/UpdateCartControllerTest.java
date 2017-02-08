package com.expert_soft.controller.cart;

import com.expert_soft.controller.ServletConstants;
import com.expert_soft.model.order.Cart;
import com.expert_soft.service.CartService;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.expert_soft.controller.ServletConstants.MSG_CODE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UpdateCartControllerTest {

    private static final Logger logger = Logger.getLogger(UpdateCartControllerTest.class);

    private MockMvc mockMvc;

    @InjectMocks
    private UpdateCartController controller;
    @Mock
    private CartService service;

    @Before
    public void setup() {
        controller = new UpdateCartController();
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void updateCart_deleteFromCart() throws Exception {
        MockHttpServletRequestBuilder requestBuilder  =
                MockMvcRequestBuilders.post("/update_cart")
                                      .param("items[0].phone.key", "1")
                                      .param("items[0].quantity", "2")
                                      .sessionAttr("cart", new Cart());
        mockMvc.perform(requestBuilder)
               .andExpect(status().is(302))
               .andExpect(flash().attribute(MSG_CODE, ServletConstants.MsgCodes.SUCCESS_UPDATE));
    }

}