package com.expert_soft.controller.cart;

import com.expert_soft.controller.ServletConstants;
import com.expert_soft.model.CartItemsContainer;
import com.expert_soft.model.OrderItem;
import com.expert_soft.model.order.Cart;
import com.expert_soft.test_util.DataBuilder;
import com.expert_soft.test_util.TestConstants;
import org.apache.log4j.Logger;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Collection;

import static com.expert_soft.controller.ServletConstants.MSG_CODE;
import static com.expert_soft.controller.ServletConstants.PHONE_TO_DELETE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        TestConstants.ROOT_CONTEXT,
        TestConstants.SERVLET_CONTEXT})
@WebAppConfiguration
@ActiveProfiles("test")
public class UpdateCartControllerIntTest {

    private static final Logger logger = Logger.getLogger(UpdateCartControllerIntTest.class);

    @Autowired
    private WebApplicationContext context;
    @Autowired private UpdateCartController controller;

    private MockMvc mockMvc;
    private Cart cartWithOneItem;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        this.cartWithOneItem = DataBuilder.Carts.byOrder_1_clean();
    }


    @Test
    public void updateCart_Valid() throws Exception {
        CartItemsContainer container = new CartItemsContainer(1);
        OrderItem item = container.getItems()[0];
        item.setQuantity(2);
        item.getPhone().setKey(1L);

        MockHttpServletRequestBuilder requestBuilder  =
                MockMvcRequestBuilders.post("/update_cart")
                                      .param("items[0].phone.key", "1")
                                      .param("items[0].quantity", "2")
                                      .sessionAttr("cart", cartWithOneItem);
        mockMvc.perform(requestBuilder)
               .andExpect(status().is(302))
               .andExpect(flash()
                       .attribute(MSG_CODE, ServletConstants.MsgCodes.SUCCESS_UPDATE));
    }

    @Test
    public void updateCart_Calculation() throws Exception {
        CartItemsContainer container = new CartItemsContainer(1);
        OrderItem item = container.getItems()[0];
        item.setQuantity(2);
        item.getPhone().setKey(1L);

        MockHttpServletRequestBuilder requestBuilder  =
                MockMvcRequestBuilders.post("/update_cart")
                                      .param("items[0].phone.key", "1")
                                      .param("items[0].quantity", "2")
                                      .param(PHONE_TO_DELETE, "1")
                                      .sessionAttr("cart", cartWithOneItem);
        mockMvc.perform(requestBuilder)
               .andExpect(status().is(302))
               .andExpect(flash()
                       .attribute(MSG_CODE, ServletConstants.MsgCodes.SUCCESS_UPDATE));
        Collection<OrderItem> orderItems = cartWithOneItem.getOrderItems();
        assertThat(orderItems, Matchers.empty());
        assertEquals(cartWithOneItem.getSubtotal(), new BigDecimal("0.00"));
    }

    @Test
    public void updateCart_Invalid() throws Exception {
        MockHttpServletRequestBuilder requestBuilder  =
                MockMvcRequestBuilders.post("/update_cart")
                                      .param("items[0].phone.key", "1")
                                      .param("items[0].quantity", "15")
                                      .sessionAttr("cart", cartWithOneItem);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        mockMvc.perform(requestBuilder)
               .andExpect(status().isOk());
    }
}
