package com.expert_soft.helper.impl;


import com.expert_soft.exception.service.ajax.AjaxException;
import com.expert_soft.helper.JsonHelper;
import com.expert_soft.model.AjaxResponseCart;
import com.expert_soft.model.Cart;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JsonHelperImpl implements JsonHelper {

    @Override
    public String write(AjaxResponseCart cart)  {

        ObjectMapper jsonObject = new ObjectMapper();
        jsonObject.getSerializationConfig()
                  .addMixInAnnotations(Cart.class, CartMixIn.class);

        jsonObject.configure(DeserializationConfig.Feature.WRAP_EXCEPTIONS, true);
        jsonObject.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        jsonObject.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return jsonObject.writerWithDefaultPrettyPrinter().writeValueAsString(cart);
        } catch (IOException e) {
            throw new AjaxException("Problem while writing ajax " + cart, e);
        }
    }
}
