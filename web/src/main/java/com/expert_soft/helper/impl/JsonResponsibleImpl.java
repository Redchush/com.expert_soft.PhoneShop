package com.expert_soft.helper.impl;


import com.expert_soft.exception.service.ajax.AjaxException;
import com.expert_soft.helper.JsonResponsible;
import com.expert_soft.model.AjaxResponseCart;

import com.expert_soft.model.order.Cart;
import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component("jsonResponsible")
public class JsonResponsibleImpl implements JsonResponsible {

    private static final Logger LOGGER = Logger.getLogger(JsonResponsibleImpl.class);

    private ObjectMapper mapper;

    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }




    @Override
    public String beautifulWrite(AjaxResponseCart cart)  {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(cart);
        } catch (IOException e) {
            throw new AjaxException("Problem while writing ajax " + cart, e);
        }
    }

    @Override
    public String simpleWrite(AjaxResponseCart cart)  {
        try {
            return mapper.writeValueAsString(cart);
        } catch (IOException e) {
            throw new AjaxException("Problem while writing ajax " + cart, e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        defaultConfigure();
    }

    public void defaultConfigure(){
        mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationConfig.Feature.WRAP_EXCEPTIONS, true);

        mapper.setVisibilityChecker(mapper.getSerializationConfig()
                                          .getDefaultVisibilityChecker()
                                          .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                                          .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                                          .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                                          .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
        mapper.getSerializationConfig()
              .addMixInAnnotations(Cart.class, CartMixIn.class);
        LOGGER.debug("Mapper configured");

    }
}
