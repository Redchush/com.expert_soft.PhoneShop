package com.expert_soft.controller.cart;

import com.expert_soft.exception.service.ajax.AjaxException;
import com.expert_soft.model.result.ValidationResult;
import com.expert_soft.service.CartResponseService;
import com.expert_soft.service.CartService;
import com.expert_soft.test_util.MockData;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.expert_soft.controller.ServletConstants.PHONE_ID_TO_ADD;
import static com.expert_soft.controller.ServletConstants.QUANTITY_PARAM;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AjaxAddToCartControllerTest {

    private static final Logger logger = Logger.getLogger(AjaxAddToCartControllerTest.class);

    private AjaxAddToCartController controller;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        controller = new AjaxAddToCartController();
        MockitoAnnotations.initMocks(this);
        CartService mock = Mockito.mock(CartService.class, Mockito.RETURNS_DEEP_STUBS);
        when(mock.addToCart(any(Long.class), any(Integer.class)))
                .thenReturn(ValidationResult.SUCCESS_VALIDATION_RESULT);

        controller.setCartService(mock);
        controller.setCartResponseService(MockData.getSingleAnswerResponseService());
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void addToCart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/add_to_cart")
                                              .param(PHONE_ID_TO_ADD, "1")
                                              .param(QUANTITY_PARAM, "1")
                                              .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void ajaxIO() throws Exception {
        try{
            CartResponseService result = Mockito.mock(CartResponseService.class);
            doThrow(AjaxException.class)
                    .when(result)
                    .buildJsonSuccess(anyObject(), anyString());

            controller.setCartResponseService(result);
            mockMvc.perform(MockMvcRequestBuilders.get("/add_to_cart")
                                                  .param(PHONE_ID_TO_ADD, "1")
                                                  .param(QUANTITY_PARAM, "1")
                                                  .accept(MediaType.APPLICATION_JSON_VALUE))
                   .andExpect(status().isInternalServerError());

        } finally {
            controller.setCartResponseService(MockData.getSingleAnswerResponseService());
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