package com.expert_soft.controller;

import com.expert_soft.model.OrderItem;
import com.expert_soft.model.Phone;
import com.expert_soft.model.order.Cart;
import com.expert_soft.service.OrderService;
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

import static com.expert_soft.controller.ServletConstants.CART_ATTR;
import static com.expert_soft.controller.ServletConstants.MSG_CODE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class CartControllerTest {

    private static final Logger logger = Logger.getLogger(ProductController.class);

    private MockMvc mockMvc;

    @InjectMocks
    private CartController controller;
    @Mock
    private OrderService sampleService;

    @Before
    public void setup() {
        controller = new CartController();
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void cart() throws Exception {
        mockMvc.perform(get("/cart"))
               .andExpect(status().isOk())
               .andExpect(view().name("emptyCart"));

        Cart fullCart = new Cart();
        fullCart.addItem(new OrderItem(new Phone(1L), 10));

        mockMvc.perform(get("/cart").sessionAttr(CART_ATTR,fullCart ))
               .andExpect(status().isOk())
               .andExpect(view().name("fullCart"));

    }

    @Test
    public void deleteFromCart() throws Exception {
        MockHttpServletRequestBuilder requestBuilder  =
                MockMvcRequestBuilders.post("/update_cart")
                                      .param("items[0].phone.key", "1")
                                      .param("items[0].quantity", "2")
                                      .sessionAttr("cart", new Cart());
        mockMvc.perform(requestBuilder)
               .andExpect(status().is(302))
               .andExpect(flash().attribute(MSG_CODE, ServletConstants.MsgCodes.SUCCESS_UPDATE));
    }

    @Test
    public void changeQuantity() throws Exception {

    }

    @Test
    public void addToCartAjax() throws Exception {

    }

}