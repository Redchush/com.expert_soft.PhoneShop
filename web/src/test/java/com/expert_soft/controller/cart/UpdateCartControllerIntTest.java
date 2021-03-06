package com.expert_soft.controller.cart;

import com.expert_soft.form.UpdateCartForm;
import com.expert_soft.model.order.Cart;
import com.expert_soft.model.order.OrderItem;
import com.expert_soft.service.impl.CartServiceImpl;
import com.expert_soft.test_util.DataBuilder;
import com.expert_soft.test_util.TestConstants;
import org.apache.log4j.Logger;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

import static com.expert_soft.controller.ServletConstants.PHONE_TO_DELETE;
import static com.expert_soft.controller.ServletConstants.TEMP_MSG;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        TestConstants.SERVLET_CONTEXT})
@WebAppConfiguration
@ActiveProfiles("test")
public class UpdateCartControllerIntTest {

    private static final Logger logger = Logger.getLogger(UpdateCartControllerIntTest.class);

    @Autowired private WebApplicationContext context;
    @Autowired private UpdateCartController controller;
    @Autowired private MessageSource source;

    @Autowired private CartServiceImpl cartService;

    private MockMvc mockMvc;
    private Cart cartWithOneItem;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        this.cartWithOneItem = DataBuilder.Carts.byOrder_1_clean();
        cartService.setCart(cartWithOneItem);
        controller.setCartService(cartService);

    }


    @Test
    public void updateCart_Valid() throws Exception {
        UpdateCartForm container = new UpdateCartForm(1);
        OrderItem item = container.getItems()[0];
        item.setQuantity(2);
        item.getPhone().setKey(1L);

        MockHttpServletRequestBuilder requestBuilder  =
                MockMvcRequestBuilders.post("/update_cart")
                                      .param("items[0].phone.key", "1")
                                      .param("items[0].quantity", "2");
        mockMvc.perform(requestBuilder)
               .andExpect(status().is(302))
               .andExpect(flash()
                       .attributeExists(TEMP_MSG));
    }

    @Test
    public void updateCart_Calculation() throws Exception {
        UpdateCartForm container = new UpdateCartForm(1);
        OrderItem item = container.getItems()[0];
        item.setQuantity(2);
        item.getPhone().setKey(1L);

        MockHttpServletRequestBuilder requestBuilder  =
                MockMvcRequestBuilders.post("/update_cart")
                                      .param("items[0].phone.key", "1")
                                      .param("items[0].quantity", "2")
                                      .param(PHONE_TO_DELETE, "1");
        mockMvc.perform(requestBuilder)
               .andExpect(status().is(302))
               .andExpect(flash()
                       .attributeExists(TEMP_MSG));
        Collection<OrderItem> orderItems = cartWithOneItem.getOrderItems();
        assertThat(orderItems, Matchers.empty());
        assertEquals(cartWithOneItem.getSubtotal(), new BigDecimal("0.00"));
    }

    @Test
    public void updateCart_Invalid() throws Exception {
        MockHttpServletRequestBuilder requestBuilder  =
                MockMvcRequestBuilders.post("/update_cart")
                                      .param("items[0].phone.key", "1")
                                      .param("items[0].quantity", "15");
        mockMvc.perform(requestBuilder)
               .andExpect(status().isOk());

    }

    @Test
    public void updateCart_NumberFormat() throws Exception {
        MockHttpServletRequestBuilder builder  =
                MockMvcRequestBuilders.post("/update_cart")
                                      .param("items[0].phone.key", "1")
                                      .param("items[0].quantity", "blabla");
        MvcResult mvcResult = mockMvc.perform(builder).andReturn();
        Map<String, Object> model = mvcResult.getModelAndView().getModel();
        System.out.println("MODEL: " + model);
        BindingResult result = (BindingResult)
                model.get("org.springframework.validation.BindingResult.cartItems");
        FieldError fieldError = result.getFieldError("items[0].quantity");

        System.out.println("CODE:" +fieldError.getCode());
        mockMvc.perform(builder)
               .andExpect(status().isOk());
    }
}
