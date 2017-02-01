package com.expert_soft.controller;

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


public class ProductControllerTest {

    private static final Logger logger = Logger.getLogger(ProductController.class);

    @InjectMocks
    private ProductController controller;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private PhoneService sampleService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void product() throws Exception {
        mockMvc.perform(get("/products/1"))
               .andExpect(status().isOk())
               .andExpect(model().attributeExists("phone"))
               .andExpect(view().name("productDetails"));
    }

    @Test
    public void product_Invalid() throws Exception {
        mockMvc.perform(get("/products/blabla"))
               .andExpect(status().isBadRequest())
               .andExpect(view().name("/error/notFound"));
    }

    @Test
    public void products() throws Exception {
        mockMvc.perform(get("/products"))
               .andExpect(status().isOk())
               .andExpect(model().attributeExists("phones"))
               .andExpect(view().name("productList"));
    }




}