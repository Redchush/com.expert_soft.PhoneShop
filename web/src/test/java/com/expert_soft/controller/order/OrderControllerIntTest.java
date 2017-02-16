package com.expert_soft.controller.order;

import com.expert_soft.model.order.Cart;
import com.expert_soft.model.order.UserInfo;
import com.expert_soft.test_util.DataBuilder;
import com.expert_soft.test_util.TestConstants;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        TestConstants.ROOT_CONTEXT,
        TestConstants.SERVLET_CONTEXT})
@WebAppConfiguration
@ActiveProfiles("test")
public class OrderControllerIntTest {

    private static final Logger logger = Logger.getLogger(OrderControllerIntTest.class);

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
        mockMvc.perform(get("/order")
               .sessionAttr("cart", new Cart()))
               .andExpect(status().isOk());
    }

    @Test
    public void doOrder_Valid() throws Exception {
        Cart cartWithOneItem = DataBuilder.Carts.byOrder_1();
        UserInfo info = DataBuilder.Order_1.getUserInfo();
        mockMvc.perform(Util.postSaveOrder(info, cartWithOneItem))
               .andExpect(status().is(302));

    }

    @Test
    public void doOrder_Invalid() throws Exception {
        Cart cartWithOneItem = DataBuilder.Carts.byOrder_1();
        UserInfo info = DataBuilder.Order_1.getUserInfo();
        info.setContactPhoneNo("InvalidPhone");

        mockMvc.perform(Util.postSaveOrder(info, cartWithOneItem))
               .andExpect(status().isOk());
    }
}