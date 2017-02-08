package com.expert_soft.service.impl;


import com.expert_soft.exception.service.ajax.AjaxException;
import com.expert_soft.helper.JsonResponsible;
import com.expert_soft.model.AjaxResponseCart;
import com.expert_soft.model.OrderItem;
import com.expert_soft.model.order.Cart;
import com.expert_soft.service.AjaxResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service("ajaxResponseService")
public class AjaxResponseServiceImpl implements AjaxResponseService{

    private JsonResponsible jsonResponsible;
    private MessageSource msgSource;

    @Value("${orderItem.max}")
    private int maxItems;

    public void setMaxItems(Integer maxItems) {
        this.maxItems = maxItems;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.msgSource = messageSource;
    }

    @Autowired
    public void setJsonResponsible(JsonResponsible jsonResponsible) {
        this.jsonResponsible = jsonResponsible;
    }


    private static final String FAIL_TO_WRITE_CODE = "ajaxCart.error.writeFail";
    private static final String ERROR_CODE = "ajaxCart.error";
    private static final String PREV_ITEM_CODE = "ajaxCart.prevItem";

    private static final String SUCCESS_CODE = "ajaxCart.success";
    private static final String INVALID_FORMAT = "ajaxCart.error.invalidFormat";
    private static final String MODEL_REPLACEMENT = "{model}";
    private static final String ERROR_INTERNAL = "error.internal";

    @Override
    public String buildAjaxSuccess(Cart cart, String model) throws AjaxException {
        String message = msgSource.getMessage(SUCCESS_CODE,
                new Object[]{model}, Locale.ROOT);
        AjaxResponseCart response =
                new AjaxResponseCart(message, 200,
                        new AjaxResponseCart.ShortCart(cart.getSubtotal(),
                                                       cart.getTotalPhonesCount()));
        return jsonResponsible.beautifulWrite(response);
    }

    @Override
    public String buildFailToWrite(){
        return msgSource.getMessage(FAIL_TO_WRITE_CODE, new Object[] {MODEL_REPLACEMENT}, null);
    }

    @Override
    public String buildInvalidFormat(){
        String message = msgSource.getMessage(INVALID_FORMAT, null,
                LocaleContextHolder.getLocale());
        return buildFail(message, null);
    }

    @Override
    public String buildFailUnexpected(){
        String message = msgSource.getMessage(ERROR_INTERNAL, null,
                LocaleContextHolder.getLocale());
        return buildFail(message, null);
    }

    @Override
    public String buildFail(String additionalMsg, OrderItem prevItem){
        String currentModel = prevItem == null ? MODEL_REPLACEMENT : prevItem.getPhone().getModel();
        String baseMessage = msgSource.getMessage(ERROR_CODE,
                                                  new Object[]{currentModel},
                                                  LocaleContextHolder.getLocale());
        String prevItemInfo = "";
        if (prevItem != null){
            int remain = maxItems - prevItem.getQuantity();
            prevItemInfo = msgSource.getMessage(PREV_ITEM_CODE,
                                                new Object[]{prevItem.getQuantity(), currentModel, remain},
                                                LocaleContextHolder.getLocale());
        }
        return String.format("%s\n%s\n%s", baseMessage, prevItemInfo, additionalMsg);
    }

    @Override
    public String buildAjaxFailInvalidFormat(Integer code) {
        String message = msgSource.getMessage(INVALID_FORMAT, null,
                LocaleContextHolder.getLocale());
        return buildAjaxFail(message, code);
    }

    @Override
    public String buildAjaxFailUnexpected() {
        String message = msgSource.getMessage(ERROR_INTERNAL, null,
                LocaleContextHolder.getLocale());
        return buildAjaxFail(message, 500);
    }

    @Override
    public String buildAjaxFail(String addMsg, Integer code) {
        String message = msgSource.getMessage(ERROR_CODE,
                new Object[]{MODEL_REPLACEMENT},
                LocaleContextHolder.getLocale())
                + "\n" + addMsg;
        AjaxResponseCart response = new AjaxResponseCart(message, code);
        response.setPattern(true);
        return jsonResponsible.simpleWrite(response);
    }


}
