package com.expert_soft.controller;

import com.expert_soft.config.ApplicationConfiguration;
import com.expert_soft.exception.service.ajax.AjaxException;
import com.expert_soft.model.Cart;
import com.expert_soft.service.AjaxResponseService;
import com.expert_soft.service.CartService;
import com.expert_soft.util.TestDataConfig;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.expert_soft.controller.ServletConstants.PHONE_ID_TO_ADD;
import static com.expert_soft.controller.ServletConstants.QUANTITY_PARAM;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfiguration.class, TestDataConfig.class} )
@WebAppConfiguration
public class AjaxCartControllerTest {


    private static final Logger logger = Logger.getLogger(ProductController.class);

    @Autowired
    private WebApplicationContext context;

    @InjectMocks
    @Autowired
    private AjaxCartController controller;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private CartService sampleService;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private AjaxResponseService responseService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
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
            controller.setResponseService(serviceThrowingException);
            mockMvc.perform(MockMvcRequestBuilders.get("/add_to_cart")
                                                  .param(PHONE_ID_TO_ADD, "1")
                                                  .param(QUANTITY_PARAM, "1")
                                                  .accept(MediaType.APPLICATION_JSON_VALUE))
                   .andExpect(status().isInternalServerError())
                   .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

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
               .andExpect(status().isUnprocessableEntity())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }



    AjaxResponseService serviceThrowingException = new AjaxResponseService() {
        @Override
        public String buildAjaxSuccess(Cart cart, String modelAdded) throws AjaxException {
            throw new AjaxException();
        }
        @Override
        public String buildFailToWrite() {
            return null;
        }

        @Override
        public String buildAjaxFailUnexpected() {
            return null;
        }

        @Override
        public String buildAjaxFail(String additionalMsg, Integer code) {
            return null;
        }

        @Override
        public String buildInvalidFormat() {
            return null;
        }

        @Override
        public String buildFailUnexpected() {
            return null;
        }

        @Override
        public String buildFail(String addititonalMsg) {
            return null;
        }

        @Override
        public String buildAjaxFailInvalidFormat(Integer code) {
            return null;
        }

        @Override
        public void setMessageSource(MessageSource messageSource) {

        }
    };

}