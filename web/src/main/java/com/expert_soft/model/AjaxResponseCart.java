package com.expert_soft.model;


import com.expert_soft.model.order.Cart;

import java.io.Serializable;

//@JsonInclude(JsonInclude.Include.NON_NULL)
public class AjaxResponseCart implements Serializable {

    private String msg;
    private Integer code;

    private Boolean pattern;
    private Cart result;

    public AjaxResponseCart() {}

    public AjaxResponseCart(String msg) {
        this.msg = msg;
    }

    public AjaxResponseCart(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }

    public AjaxResponseCart(String msg, Integer code, Cart result) {
        this.msg = msg;
        this.code = code;
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Cart getResult() {
        return result;
    }

    public void setResult(Cart result) {
        this.result = result;
    }

    public Boolean getPattern() {
        return pattern;
    }

    public void setPattern(Boolean pattern) {
        this.pattern = pattern;
    }
}
