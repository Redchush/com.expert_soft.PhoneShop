package com.expert_soft.controller;

import com.expert_soft.config.ApplicationConfig;
import com.expert_soft.model.CartItemsContainer;
import com.expert_soft.model.OrderItem;
import com.expert_soft.model.UserInfo;
import com.expert_soft.model.order.Cart;
import com.expert_soft.test_util.DataBuilder;
import com.expert_soft.validator.group.G_Order;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static com.expert_soft.controller.ServletConstants.CART_ATTR;
import static com.expert_soft.controller.ServletConstants.MSG_CODE;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class} )
@WebAppConfiguration
@ActiveProfiles("test")
public class OrderControllerIntTest {

    private static final Logger logger = Logger.getLogger(ProductController.class);

    @Autowired
    private OrderController controller;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void order() throws Exception {

    }

    @Test
    public void doOrder_Valid() throws Exception {
        Cart cartWithOneItem = DataBuilder.Carts.byOrder_1();
        UserInfo info = DataBuilder.Order_1.getUserInfo();

        MockHttpServletRequestBuilder requestBuilder  =
                MockMvcRequestBuilders.post("/doOrder")
                                      .param("firstName", info.getFirstName())
                                      .param("lastName", info.getLastName())
                                      .param("deliveryAddress", info.getDeliveryAddress())
                                      .param("contactPhoneNo", info.getContactPhoneNo())
                                      .sessionAttr("cart", cartWithOneItem);
        mockMvc.perform(requestBuilder)
               .andExpect(status().is(302));

    }

    @Test
    public void doOrder_Invalid() throws Exception {
        Cart cartWithOneItem = DataBuilder.Carts.byOrder_1();

        UserInfo info = DataBuilder.Order_1.getUserInfo();
        info.setContactPhoneNo("InvalidPhone");

        MockHttpServletRequestBuilder requestBuilder  =
                MockMvcRequestBuilders.post("/doOrder")
                                      .param("firstName", info.getFirstName())
                                      .param("lastName", info.getLastName())
                                      .param("deliveryAddress", info.getDeliveryAddress())
                                      .param("contactPhoneNo", info.getContactPhoneNo())
                                      .sessionAttr("cart", cartWithOneItem);
        mockMvc.perform(requestBuilder)
               .andExpect(status().isOk());
    }

    @Test
    public void confirmOrder() throws Exception {

    }


}