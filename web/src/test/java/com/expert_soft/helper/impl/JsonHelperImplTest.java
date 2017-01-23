package com.expert_soft.helper.impl;

import com.expert_soft.helper.JsonHelper;
import com.expert_soft.model.AjaxResponseCart;
import com.expert_soft.model.Cart;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:web_test-bean.xml",
        "classpath:helper_context.xml"
})

public class JsonHelperImplTest {

    @Autowired @Qualifier("cart_2") Cart cart;
    @Autowired JsonHelper helper;

    @Test
    public void write() throws Exception {
        AjaxResponseCart expected = new AjaxResponseCart();
        expected.setCode("200");
        expected.setMsg("success");
        expected.setResult(cart);

        String write = helper.write(expected);
        System.out.println(write);
        ObjectMapper mapper= new ObjectMapper();


        AjaxResponseCart actual = mapper.readValue(write, AjaxResponseCart.class);
        JsonNode jsonNode = mapper.readTree(write);
        JsonNode result = jsonNode.get("result");
        JsonNode cartMap = result.get("itemsMap");

        assertNull("Fail to exclude map. Result" + write, cartMap);
        assertEquals(expected.getCode(), actual.getCode());
        assertEquals(expected.getMsg(), actual.getMsg());
        assertEquals(expected.getResult().getCartSize(), actual.getResult().getCartSize());
        assertEquals(expected.getResult().getSubtotal(), actual.getResult().getSubtotal());

    }

}