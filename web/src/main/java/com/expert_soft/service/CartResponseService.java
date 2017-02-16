package com.expert_soft.service;


import com.expert_soft.exception.service.ajax.AjaxException;
import com.expert_soft.model.order.Cart;
import com.expert_soft.model.order.OrderItem;
import org.springframework.context.MessageSourceAware;

public interface CartResponseService extends MessageSourceAware{

    String buildJsonSuccess(Cart cart, String modelAdded) throws AjaxException;

    String getUpdateSuccessMsg();

    String getFailUpdateMsg();

    String getInvalidFormatMsg();

    String buildFailToWrite();

    String buildAjaxFailUnexpected();

    String buildAjaxFail(String additionalMsg, Integer code);

    String buildInvalidFormat();

    String buildFailUnexpected();

    String buildFail(String additionalMsg, OrderItem prevItem);

    String buildAjaxFailInvalidFormat(Integer code);

}
