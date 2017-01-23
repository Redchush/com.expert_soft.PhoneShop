package com.expert_soft.helper;


import com.expert_soft.exception.service.ajax.AjaxException;
import com.expert_soft.model.AjaxResponseCart;

public interface JsonHelper {

    String write(AjaxResponseCart cart) throws AjaxException;
}
