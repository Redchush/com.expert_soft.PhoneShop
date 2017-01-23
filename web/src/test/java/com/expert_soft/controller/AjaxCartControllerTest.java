package com.expert_soft.controller;

import com.expert_soft.service.AjaxResponseService;
import com.expert_soft.service.CartService;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.expert_soft.model.ServletConstants.PHONE_TO_DELETE;
import static com.expert_soft.model.ServletConstants.QUANTITY_PARAM;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:test-root-context.xml",
        "classpath:test-servlet-context.xml",
        "classpath:web_test-bean.xml"
})

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
        MockHttpServletRequestBuilder requestBuilder  =
                MockMvcRequestBuilders.get("/add_to_cart")
                                      .param(PHONE_TO_DELETE, "1")
                                      .param(QUANTITY_PARAM, "1")
                                      .accept(MediaType.APPLICATION_JSON_VALUE);
        MockHttpServletRequest req = requestBuilder
                .buildRequest(context.getServletContext());

//        debugRequest(req);
        mockMvc.perform(requestBuilder)
               .andExpect(status().isOk());
    }

    @Test
    public void ajaxIO() throws Exception {

    }

    @Test
    public void orderAjaxItemViolation() throws Exception {

    }

}