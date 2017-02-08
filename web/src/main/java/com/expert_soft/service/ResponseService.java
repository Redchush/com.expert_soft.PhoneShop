package com.expert_soft.service;


import com.expert_soft.exception.service.ajax.AjaxException;
import com.expert_soft.model.OrderItem;
import com.expert_soft.model.order.Cart;
import org.springframework.context.MessageSourceAware;

public interface ResponseService extends MessageSourceAware{

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
