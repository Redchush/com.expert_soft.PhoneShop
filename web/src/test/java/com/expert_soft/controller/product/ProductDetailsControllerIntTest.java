package com.expert_soft.controller.product;

import com.expert_soft.model.Phone;
import com.expert_soft.test_util.DataBuilder;
import com.expert_soft.test_util.TestConstants;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        TestConstants.ROOT_CONTEXT,
        TestConstants.SERVLET_CONTEXT})
@WebAppConfiguration
@ActiveProfiles("test")
public class ProductDetailsControllerIntTest {

    private static final Logger logger = Logger.getLogger(ProductDetailsControllerIntTest.class);

    @Autowired
    private ProductDetailsController controller;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void product_Valid() throws Exception {
        Phone phone_id_1 = DataBuilder.getPhoneId_1();
        mockMvc.perform(get("/products/1"))
               .andExpect(model().attribute("phone", phone_id_1));
    }

    @Test
    public void product_Invalid() throws Exception {
        mockMvc.perform(get("/products/100"))
               .andExpect(status().isNotFound());

        mockMvc.perform(get("/products/-1"))
               .andExpect(status().isNotFound());

        mockMvc.perform(get("/products/blabla"))
               .andExpect(status().isBadRequest());

    }

}
