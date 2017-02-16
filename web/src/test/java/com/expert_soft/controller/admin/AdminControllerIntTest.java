package com.expert_soft.controller.admin;


import com.expert_soft.model.order.Order;
import com.expert_soft.model.order.OrderStatus;
import com.expert_soft.service.OrderService;
import com.expert_soft.test_util.TestConstants;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        TestConstants.ROOT_CONTEXT,
        TestConstants.SERVLET_CONTEXT})
@WebAppConfiguration
@ActiveProfiles("test")
public class AdminControllerIntTest {

    @Autowired private WebApplicationContext context;
    @Autowired private AdminController controller;
    @Autowired private OrderService service;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void updateOrder() throws Exception {
        Order order = service.getOrder(1L);
        assertEquals(order.getStatus(), OrderStatus.NEW);
        mockMvc.perform(post("/update_order")
               .param("orderKey", "1").param("status", OrderStatus.DELIVERED.toString()))
               .andExpect(status().is3xxRedirection());
        Order order1 = service.getOrder(1L);
        assertEquals(OrderStatus.DELIVERED, order1.getStatus());

    }

    @Test
    public void admin()throws Exception {
        int ordersCount = 6;
        mockMvc.perform(get("/admin"))
               .andExpect(status().isOk())
               .andExpect(model()
                       .attribute("orders", Matchers.hasSize(ordersCount)));
    }

}
