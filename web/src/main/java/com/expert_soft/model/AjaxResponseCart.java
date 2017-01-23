package com.expert_soft.model;


import java.io.Serializable;



//@JsonInclude(JsonInclude.Include.NON_NULL)
public class AjaxResponseCart implements Serializable {

    private String msg;
    private String code;

    private Cart result;

    public AjaxResponseCart() {}

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Cart getResult() {
        return result;
    }

    public void setResult(Cart result) {
        this.result = result;
    }


}
