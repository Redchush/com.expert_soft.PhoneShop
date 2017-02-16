package com.expert_soft.controller.login;

import com.expert_soft.controller.ServletConstants;
import com.expert_soft.test_util.TestConstants;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        TestConstants.ROOT_CONTEXT,
        TestConstants.SERVLET_CONTEXT})
@WebAppConfiguration
@ActiveProfiles("test")
public class LoginControllerIntTest {


    @Autowired private WebApplicationContext context;
    @Autowired private LoginController controller;

    private MockMvc mockMvc;

    @Before
    public void setup() {
       this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void login() throws Exception {
        mockMvc.perform(get("/login"))
               .andExpect(view().name("logIn"));

        mockMvc.perform(get("/login").param("error", ""))
               .andExpect(model().attributeExists(ServletConstants.ERROR_MSG));

        mockMvc.perform(get("/login").param("logout", ""))
               .andExpect(model().attributeExists(ServletConstants.SUCCESS_MSG));
    }
}
