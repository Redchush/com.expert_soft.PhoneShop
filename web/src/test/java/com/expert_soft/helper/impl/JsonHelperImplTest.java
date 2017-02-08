package com.expert_soft.helper.impl;

import com.expert_soft.model.AjaxResponseCart;
import com.expert_soft.model.order.Cart;
import com.expert_soft.test_util.DataBuilder;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;



public class JsonHelperImplTest {


    @Test
    public void write() throws Exception {
        JsonResponsibleImpl helper = new JsonResponsibleImpl();
        helper.setMapper(new ObjectMapper());
        helper.afterPropertiesSet();

        Cart cart = DataBuilder.Carts.byOrder_2();

        AjaxResponseCart expected = new AjaxResponseCart();
        expected.setCode(200);
        expected.setMsg("success");
        expected.setResult(new AjaxResponseCart.ShortCart(cart.getSubtotal(), cart.getTotalPhonesCount()));

        String write = helper.beautifulWrite(expected);
        System.out.println(write);
        ObjectMapper mapper= new ObjectMapper();


        AjaxResponseCart actual = mapper.readValue(write, AjaxResponseCart.class);
        JsonNode jsonNode = mapper.readTree(write);
        JsonNode result = jsonNode.get("result");
        int actualResultChildNodes = result.size();
        JsonNode cartMap = result.get("orderItems");

        assertNull("Fail to exclude map. ValidationResult" + write, cartMap);
        assertEquals("Fail to exclude map. ValidationResult" + write, actualResultChildNodes, 2);
        assertEquals(expected.getCode(), actual.getCode());
        assertEquals(expected.getMsg(), actual.getMsg());
        assertEquals(expected.getResult().getTotalPhonesCount(),
                actual.getResult().getTotalPhonesCount());
        assertEquals(expected.getResult().getSubtotal(), actual.getResult().getSubtotal());



    }

}