package com.expert_soft.model;


import java.io.Serializable;
import java.math.BigDecimal;

//@JsonInclude(JsonInclude.Include.NON_NULL)
public class AjaxResponseCart implements Serializable {

    private String msg;
    private Integer code;

    private Boolean pattern;
    private ShortCart result;

    public AjaxResponseCart() {}

    public AjaxResponseCart(String msg) {
        this.msg = msg;
    }

    public AjaxResponseCart(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }

    public AjaxResponseCart(String msg, Integer code, ShortCart result) {
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

    public ShortCart getResult() {
        return result;
    }

    public void setResult(ShortCart result) {
        this.result = result;
    }

    public Boolean getPattern() {
        return pattern;
    }

    public void setPattern(Boolean pattern) {
        this.pattern = pattern;
    }

    public static class ShortCart{

        private BigDecimal subtotal;
        private Integer totalPhonesCount;

        public ShortCart() {}

        public ShortCart(BigDecimal subtotal, Integer totalPhonesCount) {
            this.subtotal = subtotal;
            this.totalPhonesCount = totalPhonesCount;
        }

        public BigDecimal getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(BigDecimal subtotal) {
            this.subtotal = subtotal;
        }

        public Integer getTotalPhonesCount() {
            return totalPhonesCount;
        }

        public void setTotalPhonesCount(Integer totalPhonesCount) {
            this.totalPhonesCount = totalPhonesCount;
        }
    }
}
