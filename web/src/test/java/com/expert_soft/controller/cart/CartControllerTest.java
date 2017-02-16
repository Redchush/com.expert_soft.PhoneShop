package com.expert_soft.controller.cart;

import com.expert_soft.model.Phone;
import com.expert_soft.model.order.Cart;
import com.expert_soft.model.order.OrderItem;
import com.expert_soft.service.CartService;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.expert_soft.controller.ServletConstants.CART_ATTR;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class CartControllerTest {

    private static final Logger logger = Logger.getLogger(CartControllerTest.class);

    private MockMvc mockMvc;
    private CartService mock;

    @Before
    public void setup() {
        CartController controller = new CartController();

        mock = Mockito.mock(CartService.class, Mockito.RETURNS_DEEP_STUBS);
        controller.setCartService(mock);

        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void cart() throws Exception {
        when(mock.isCartEmpty()).thenReturn(true);

        mockMvc.perform(get("/cart").sessionAttr(CART_ATTR, new Cart()))
               .andExpect(status().isOk())
               .andExpect(view().name("emptyCart"));
    }


    @Test
    public void fullCart() throws Exception{
        Cart fullCart = new Cart();
        fullCart.addItem(new OrderItem(new Phone(1L), 10));

        when(mock.isCartEmpty()).thenReturn(false);
        mockMvc.perform(get("/cart").sessionAttr(CART_ATTR,fullCart ))
               .andExpect(status().isOk())
               .andExpect(view().name("fullCart"));
    }

}