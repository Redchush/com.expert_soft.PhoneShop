package com.expert_soft.helper;


import com.expert_soft.exception.service.ajax.AjaxException;
import com.expert_soft.model.AjaxResponseCart;
import org.springframework.beans.factory.InitializingBean;

public interface JsonHelper extends InitializingBean {

    String buautifulWrite(AjaxResponseCart cart) throws AjaxException;
    String simpleWrite(AjaxResponseCart cart) throws AjaxException;
}
