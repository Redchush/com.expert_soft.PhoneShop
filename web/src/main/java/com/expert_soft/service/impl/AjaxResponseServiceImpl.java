package com.expert_soft.service.impl;


import com.expert_soft.exception.service.ajax.AjaxException;
import com.expert_soft.helper.JsonHelper;
import com.expert_soft.model.AjaxResponseCart;
import com.expert_soft.model.Cart;
import com.expert_soft.service.AjaxResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class AjaxResponseServiceImpl implements AjaxResponseService{


    private JsonHelper jsonHelper;
    private MessageSource msgSource;

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.msgSource = messageSource;
    }

    @Autowired
    public void setJsonHelper(JsonHelper jsonHelper) {
        this.jsonHelper = jsonHelper;
    }


    private static final String FAIL_TO_WRITE = "ajaxCart.error.writeFail";
    private static final String ERROR_CODE = "ajaxCart.error";
    private static final String SUCCESS_CODE = "ajaxCart.success";
    private static final String INVALID_FORMAT = "ajaxCart.error.invalidFormat";
    private static final String MODEL_REPLACEMENT = "{model}";
    private static final String ERROR_INTERNAL = "error.internal";

    @Override
    public String buildAjaxSuccess(Cart cart, String model) throws AjaxException {
        String message = msgSource.getMessage(SUCCESS_CODE,
                new Object[]{model}, Locale.ROOT);
        AjaxResponseCart response = new AjaxResponseCart(message, 200, cart);
        return jsonHelper.buautifulWrite(response);
    }

    @Override
    public String buildFailToWrite(){
        return msgSource.getMessage(FAIL_TO_WRITE, new Object[] {MODEL_REPLACEMENT}, null);
    }

    @Override
    public String buildInvalidFormat(){
        String message = msgSource.getMessage(INVALID_FORMAT, null,
                LocaleContextHolder.getLocale());
        return buildFail(message);
    }

    @Override
    public String buildFailUnexpected(){
        String message = msgSource.getMessage(ERROR_INTERNAL, null,
                LocaleContextHolder.getLocale());
        return buildFail(message);
    }


    @Override
    public String buildFail(String addititonalMsg){
        return msgSource.getMessage(ERROR_CODE,
                new Object[]{MODEL_REPLACEMENT},
                LocaleContextHolder.getLocale())
                + "\n" + addititonalMsg;
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
        return jsonHelper.simpleWrite(response);
    }


}
