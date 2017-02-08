package com.expert_soft.controller.product;

import com.expert_soft.service.PhoneService;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductDetailsControllerTest {


    private static final Logger logger = Logger.getLogger(ProductDetailsControllerTest.class);

    @InjectMocks
    private ProductDetailsController controller;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private PhoneService sampleService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void invalidKey() throws Exception {
        mockMvc.perform(get("/products/blabla"))
               .andExpect(status().isBadRequest())
               .andExpect(view().name("/error/notFound"));
    }

    @Test
    public void product() throws Exception {
        mockMvc.perform(get("/products/1"))
               .andExpect(status().isOk())
               .andExpect(model().attributeExists("phone"))
               .andExpect(view().name("productDetails"));
    }
}