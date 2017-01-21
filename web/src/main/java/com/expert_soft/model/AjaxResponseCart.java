package com.expert_soft.model;


import java.io.Serializable;



//@JsonInclude(JsonInclude.Include.NON_NULL)
public class AjaxResponseCart implements Serializable {

    private String msg;
    private String code;
    private com.expert_soft.model.excluded.CartCurriculum result;

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

    public com.expert_soft.model.excluded.CartCurriculum getResult() {
        return result;
    }

    public void setResult(com.expert_soft.model.excluded.CartCurriculum result) {
        this.result = result;
    }


}
