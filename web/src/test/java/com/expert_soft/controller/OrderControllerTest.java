package com.expert_soft.controller;


import org.junit.Before;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class OrderControllerTest {

    private MockMvc mockMvc;

    @Before
    public void setup() {
        OrderController controller = new OrderController();
           this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
}
