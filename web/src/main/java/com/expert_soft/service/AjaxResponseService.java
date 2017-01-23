package com.expert_soft.service;


import com.expert_soft.exception.service.ajax.AjaxException;
import com.expert_soft.model.AjaxResponseCart;
import com.expert_soft.model.Cart;

public interface AjaxResponseService {
    AjaxResponseCart buildSuccess(Cart cart, String modelAdded);
    AjaxResponseCart buildFail(Cart cart, String modelNotAdded);

    String buildSuccessAndWrite(Cart cart, String modelAdded) throws AjaxException;

    String buildFailToWrite();


}
