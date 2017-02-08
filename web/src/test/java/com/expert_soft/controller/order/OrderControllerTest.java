package com.expert_soft.controller.order;


import com.expert_soft.model.order.Cart;
import com.expert_soft.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class OrderControllerTest {

    private MockMvc mockMvc;

    @Before
    public void setup() {
        OrderController controller = new OrderController();
        controller.setOrderService(Mockito.mock(OrderService.class));
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void order() throws Exception {
        mockMvc.perform(get("/order")
               .sessionAttr("cart", new Cart()))
               .andExpect(status().isOk())
               .andExpect(view().name("newOrder"));
    }

    @Test
    public void orderWithoutCart() throws Exception {

    }

}
