package com.expert_soft.helper;


import com.expert_soft.exception.service.ajax.AjaxException;
import com.expert_soft.model.AjaxResponseCart;
import org.springframework.beans.factory.InitializingBean;

public interface JsonResponsible extends InitializingBean {

    String beautifulWrite(AjaxResponseCart cart) throws AjaxException;
    String simpleWrite(AjaxResponseCart cart) throws AjaxException;
}
