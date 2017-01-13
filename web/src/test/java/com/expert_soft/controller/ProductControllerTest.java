package com.expert_soft.controller;

import com.expert_soft.model.Phone;
import com.expert_soft.service.PhoneService;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static junit.framework.TestCase.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:root-context.xml",
        "classpath:servlet-context.xml",
        "classpath:test-data.xml"
})

@WebAppConfiguration

public class ProductControllerTest {

    private static final Logger logger = Logger.getLogger(ProductController.class);

    @Autowired
    private WebApplicationContext context;

    @InjectMocks
    private ProductController controller;

    @Mock
    private PhoneService sampleService;

    private MockMvc mockMvc;
    private Phone phone_id_1 ;


    @Before
    public void setup() {
        // Process mock annotations
        MockitoAnnotations.initMocks(this);
        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        phone_id_1 = (Phone) context.getBean("phone_id_1");
    }

    @Test
    public void product() throws Exception {
        mockMvc.perform(get("/product/1"))
               .andExpect(status().isOk())
               .andExpect(model().attributeExists("phone"))
               .andExpect(view().name("productDetails"));
    }
    @Test
    public void products() throws Exception {
        mockMvc.perform(get("/products"))
               .andExpect(status().isOk())
               .andExpect(model().attributeExists("phones"))
               .andExpect(view().name("productList"));
    }


}