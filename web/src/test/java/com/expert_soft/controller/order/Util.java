package com.expert_soft.controller.order;


import com.expert_soft.model.UserInfo;
import com.expert_soft.model.order.Cart;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class Util {

    static MockHttpServletRequestBuilder addUserInfo(
                                                    MockHttpServletRequestBuilder requestBuilder,
                                                    UserInfo info){
        requestBuilder.param("firstName", info.getFirstName())
                      .param("lastName", info.getLastName())
                      .param("deliveryAddress", info.getDeliveryAddress())
                      .param("contactPhoneNo", info.getContactPhoneNo());
        return requestBuilder;
    }

    static MockHttpServletRequestBuilder postDoOrder(UserInfo info, Cart cart){
        MockHttpServletRequestBuilder requestBuilder  = MockMvcRequestBuilders.post("/saveOrder");
        Util.addUserInfo(requestBuilder, info)
            .sessionAttr("cart", cart);
        return requestBuilder;
    }
}
