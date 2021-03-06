package com.expert_soft.controller.cart;

import com.expert_soft.service.CartService;
import com.expert_soft.test_util.MockData;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.expert_soft.controller.ServletConstants.TEMP_MSG;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UpdateCartControllerTest {

    private static final Logger logger = Logger.getLogger(UpdateCartControllerTest.class);

    private MockMvc mockMvc;

    @InjectMocks
    private UpdateCartController controller;
    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private CartService service;

    @Before
    public void setup() {
        controller = new UpdateCartController();
        MockitoAnnotations.initMocks(this);
        controller.setCartResponseService(MockData.getSingleAnswerResponseService());
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void updateCart_deleteFromCart() throws Exception {
        MockHttpServletRequestBuilder requestBuilder  =
                MockMvcRequestBuilders.post("/update_cart")
                                      .param("items[0].phone.key", "1")
                                      .param("items[0].quantity", "2");
        mockMvc.perform(requestBuilder)
               .andExpect(status().is(302))
               .andExpect(flash().attribute(TEMP_MSG, MockData.STUB_ANSWER));
    }

}