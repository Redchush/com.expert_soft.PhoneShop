package com.expert_soft.controller.product;

import com.expert_soft.model.Phone;
import com.expert_soft.test_util.DataBuilder;
import com.expert_soft.test_util.TestConstants;
import com.expert_soft.test_util.asserts.ModelMatchers;
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

import static com.expert_soft.controller.ServletConstants.PHONE_LIST;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        TestConstants.ROOT_CONTEXT,
        TestConstants.SERVLET_CONTEXT})
@WebAppConfiguration
@ActiveProfiles("test")
public class ProductListControllerIntTest {

    private static final Logger logger = Logger.getLogger(ProductListControllerIntTest.class);

    @Autowired
    private ProductListController controller;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void products() throws Exception {
        Phone phoneId_1 = DataBuilder.getPhoneId_1();
        mockMvc.perform(get("/products"))
               .andExpect(model().attribute(PHONE_LIST, hasItem( phoneId_1)))
               .andExpect(model()
                       .attribute(PHONE_LIST,
                       ModelMatchers.hasPhone(phoneId_1)));
    }



}
