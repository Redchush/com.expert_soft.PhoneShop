package com.expert_soft.service.impl;


import com.expert_soft.exception.service.ajax.AjaxException;
import com.expert_soft.helper.JsonHelper;
import com.expert_soft.model.AjaxResponseCart;
import com.expert_soft.model.Cart;
import com.expert_soft.service.AjaxResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AjaxResponseServiceImpl implements AjaxResponseService {

    private JsonHelper jsonHelper;
    @Autowired
    public void setJsonHelper(JsonHelper jsonHelper) {
        this.jsonHelper = jsonHelper;
    }

    private static final String FAIL_TO_WRITE = "Sorry, we can't calculate metadata of you cart, " +
            "but phone successfully was added to cart. ";



    @Override
    public AjaxResponseCart buildSuccess(Cart cart, String modelAdded) {
        AjaxResponseCart response = new AjaxResponseCart();
        response.setResult(cart);
        response.setCode("200");
        response.setMsg("Model of phone " + modelAdded + " successfully added to cart");
        return response;
    }

    @Override
    public String buildSuccessAndWrite(Cart cart, String modelAdded) throws AjaxException {
        AjaxResponseCart cart1 = buildSuccess(cart, modelAdded);
        return jsonHelper.write(cart1);
    }

    public String buildFailToWrite(){
        return FAIL_TO_WRITE;
    }

    @Override
    public AjaxResponseCart buildFail(Cart cart, String modelNotAdded) {
        AjaxResponseCart response = new AjaxResponseCart();
        return response;
    }
}
