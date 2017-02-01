package com.expert_soft.controller;

import com.expert_soft.model.order.Cart;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.expert_soft.controller.ServletConstants.CART_ATTR;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


public class CartControllerTest {

    private static final Logger logger = Logger.getLogger(ProductController.class);

    private MockMvc mockMvc;

    @Before
    public void setup() {
        CartController controller = new CartController();
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void cart() throws Exception {
        mockMvc.perform(get("/cart"))
               .andExpect(status().isOk())
               .andExpect(view().name("emptyCart"));

        mockMvc.perform(get("/cart").sessionAttr(CART_ATTR, new Cart()))
               .andExpect(status().isOk())
               .andExpect(view().name("fullCart"));

    }

    @Test
    public void deleteFromCart() throws Exception {

    }

    @Test
    public void changeQuantity() throws Exception {

    }

    @Test
    public void addToCartAjax() throws Exception {

    }

}