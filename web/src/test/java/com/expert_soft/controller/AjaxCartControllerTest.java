package com.expert_soft.controller;

import com.expert_soft.config.ApplicationConfig;
import com.expert_soft.exception.service.ajax.AjaxException;
import com.expert_soft.model.order.Cart;
import com.expert_soft.service.AjaxResponseService;
import com.expert_soft.service.OrderService;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.expert_soft.controller.ServletConstants.PHONE_ID_TO_ADD;
import static com.expert_soft.controller.ServletConstants.QUANTITY_PARAM;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class} )
@WebAppConfiguration
@ActiveProfiles("test")
public class AjaxCartControllerTest {

    private static final Logger logger = Logger.getLogger(ProductController.class);

    @InjectMocks
    private AjaxCartController controller;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private OrderService sampleService;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private AjaxResponseService responseService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        controller = new AjaxCartController();
        MockitoAnnotations.initMocks(this);
        controller.setCart(new Cart());
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    }

    @Test
    public void addToCart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/add_to_cart")
                                              .param(PHONE_ID_TO_ADD, "1")
                                              .param(QUANTITY_PARAM, "1")
                                              .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk());
    }

    @Test
    public void ajaxIO() throws Exception {
        try{

            AjaxResponseService result = Mockito.mock(AjaxResponseService.class);
            doThrow(AjaxException.class)
                    .when(result)
                    .buildAjaxSuccess(anyObject(), anyString());

            controller.setResponseService(result);
            mockMvc.perform(MockMvcRequestBuilders.get("/add_to_cart")
                                                  .param(PHONE_ID_TO_ADD, "1")
                                                  .param(QUANTITY_PARAM, "1")
                                                  .accept(MediaType.APPLICATION_JSON_VALUE))
                   .andExpect(status().isInternalServerError());

        } finally {
            controller.setResponseService(responseService);
        }
    }


    @Test
    public void ajaxNumberFormatViolation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/add_to_cart")
                                              .param(PHONE_ID_TO_ADD, "1")
                                              .param(QUANTITY_PARAM, "blabla")
                                              .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isUnprocessableEntity());
    }





}