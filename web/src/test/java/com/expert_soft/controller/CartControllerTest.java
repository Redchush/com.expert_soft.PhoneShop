package com.expert_soft.controller;

import com.expert_soft.config.ApplicationConfiguration;
import com.expert_soft.model.Cart;
import com.expert_soft.service.CartService;
import com.expert_soft.util.TestDataConfig;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.expert_soft.controller.ServletConstants.CART_ATTR;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfiguration.class, TestDataConfig.class} )
@WebAppConfiguration
public class CartControllerTest {

    private static final Logger logger = Logger.getLogger(ProductController.class);

    @Autowired
    private WebApplicationContext context;

    @InjectMocks
    private CartController controller;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private CartService sampleService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void setCartService() throws Exception {

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