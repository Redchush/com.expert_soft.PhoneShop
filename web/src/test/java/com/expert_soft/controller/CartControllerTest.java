package com.expert_soft.controller;

import com.expert_soft.model.AjaxResponseCart;
import com.expert_soft.model.excluded.CartCurriculum;
import com.expert_soft.service.CartService;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.node.ObjectNode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Enumeration;

import static com.expert_soft.model.ServletConstants.PHONE_TO_DELETE;
import static com.expert_soft.model.ServletConstants.QUANTITY_PARAM;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:test-root-context.xml",
        "classpath:test-servlet-context.xml",
        "classpath:web_test-bean.xml"
})

@WebAppConfiguration
public class CartControllerTest {

    private static final Logger logger = Logger.getLogger(ProductController.class);

    @Autowired
    private WebApplicationContext context;

    @InjectMocks
    private CartController controller;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private CartService sampleService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        // Process mock annotations
        MockitoAnnotations.initMocks(this);
        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    }

    @Test
    public void setCartService() throws Exception {

    }

    @Test
    public void cart() throws Exception {

    }

    @Test
    public void addToCart() throws Exception {
        logger.debug("in add to cart");
        MockHttpServletRequestBuilder requestBuilder  =
                MockMvcRequestBuilders.get("/add_to_cart")
                                      .param(PHONE_TO_DELETE, "1")
                                      .param(QUANTITY_PARAM, "1")
                                      .accept(MediaType.APPLICATION_JSON_VALUE);
        MockHttpServletRequest req = requestBuilder
                .buildRequest(context.getServletContext());

//        debugRequest(req);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MvcResult perform = mockMvc.perform(options("/add_to_cart")).andReturn();

        MockHttpServletResponse response = perform.getResponse();

        String header = mvcResult.getResponse().getHeader(HttpHeaders.CONTENT_TYPE);
        logger.debug("ContentType: " + header);


        mockMvc.perform(requestBuilder).andExpect(status().isOk())
               .andExpect(content()
               .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andReturn();
    }

    private void debugResponse(MockHttpServletResponse response){

    }

    @Test
    public void writeCart() throws IOException {
        CartCurriculum curriculum = new CartCurriculum();
        curriculum.setCartSubtotal(new BigDecimal(100));
        curriculum.setCartSize(100);
        AjaxResponseCart cart = new AjaxResponseCart();
        cart.setResult(curriculum);
        cart.setMsg("msg");
        cart.setCode("200");
        write(cart);



    }

    private void write(AjaxResponseCart cart) throws IOException {
        ObjectMapper jsonObject = new ObjectMapper();
        jsonObject.configure(DeserializationConfig.Feature.WRAP_EXCEPTIONS, true);
        jsonObject.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        jsonObject.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String string = jsonObject.writerWithDefaultPrettyPrinter().writeValueAsString(cart);
        logger.debug(string);
    }

    private void debugRequest(MockHttpServletRequest request) throws IOException {
        ObjectNode jsonNodes = buildObject(request);
        logger.debug("NOde" + new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(jsonNodes));
    }



    private ObjectNode buildObject(MockHttpServletRequest req) throws IOException {

        ObjectMapper jsonObject = new ObjectMapper();
        jsonObject.configure(DeserializationConfig.Feature.WRAP_EXCEPTIONS, true);
        jsonObject.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);

        jsonObject.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        jsonObject.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        jsonObject.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, true);
        jsonObject.configure(SerializationConfig.Feature.SORT_PROPERTIES_ALPHABETICALLY, true);
//        String string ="";
//        try {
//            string = jsonObject.writerWithDefaultPrettyPrinter()
//                               .writeValueAsString(req);
//        } catch (UnsupportedOperationException e) {
//            logger.debug("writer" + string);
//        }

//        String string = jsonObject.writerWithDefaultPrettyPrinter()
//                                  .writeValueAsString();


        ObjectNode requestNode = jsonObject.createObjectNode();
//        ObjectNode headers = requestNode.putObject("headers");
//        headers.put("headerNames", string);
//        requestNode.putAll(Collections.list(req.getHeaderNames()).toString());

        requestNode.put("httpMethod", req.getMethod());
        requestNode.put("clientIP", req.getRemoteAddr());
        requestNode.put("URI:", req.getRequestURI());
        requestNode.put("URL:", req.getRequestURL().toString());
        requestNode.put("queryString", req.getQueryString());
        requestNode.put("paramNames",
                Collections.list(req.getParameterNames()).toString());
        requestNode.put("headerNames", Collections.list(req.getHeaderNames()).toString());


//        ObjectNode node =
//        requestNode.put("headers",
//                Collections.list(req.getParameterNames()).toString());


        Enumeration<String> headerNames = req.getHeaderNames();



        return requestNode;

    }


    @Test
    public void deleteFromCart() throws Exception {

    }

    @Test
    public void changeQuantity() throws Exception {

    }

    @Test
    public void addToCartAjax() throws Exception {

    }

}