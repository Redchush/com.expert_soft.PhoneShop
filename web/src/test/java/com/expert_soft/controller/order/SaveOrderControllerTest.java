package com.expert_soft.controller.order;

import com.expert_soft.model.UserInfo;
import com.expert_soft.model.order.Cart;
import com.expert_soft.service.OrderService;
import com.expert_soft.test_util.DataBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class SaveOrderControllerTest {

    private MockMvc mockMvc;
    private SaveOrderController controller;

    @Before
    public void setup() {
        controller = new SaveOrderController();
        controller.setOrderService(Mockito.mock(OrderService.class));
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void saveOrder() throws Exception {
        Cart cartWithOneItem = DataBuilder.Carts.byOrder_1();
        UserInfo info = DataBuilder.Order_1.getUserInfo();

        mockMvc.perform(Util.postSaveOrder(info, cartWithOneItem))
               .andExpect(status().is(302));
    }

    @Test
    public void saveOrder_url() throws Exception {
        Cart cartWithOneItem = DataBuilder.Carts.byOrder_1();
        UserInfo info = DataBuilder.Order_1.getUserInfo();

        mockMvc.perform(Util.postSaveOrder(info, cartWithOneItem))
               .andExpect(status().is(302))
               .andExpect(view().name("redirect:order/{orderId}"));
    }

}