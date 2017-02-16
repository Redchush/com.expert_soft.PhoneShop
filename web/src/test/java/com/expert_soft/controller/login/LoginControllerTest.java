package com.expert_soft.controller.login;

import com.expert_soft.controller.ServletConstants;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class LoginControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private LoginController controller;

    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private MessageSource messageSource;

    @Before
    public void setup() {

        controller = new LoginController();
        MockitoAnnotations.initMocks(this);
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