package com.expert_soft.model.excluded;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CartCurriculum implements Serializable{

    private Integer cartSize;
    private BigDecimal cartSubtotal;

    private Map<Long, Long> cartMap;

    public CartCurriculum() {
        cartMap = new HashMap<>();
    }

    public Integer getCartSize() {
        return cartSize;
    }

    public void setCartSize(Integer cartSize) {
        this.cartSize = cartSize;
    }

    public BigDecimal getCartSubtotal() {
        return cartSubtotal;
    }

    public void setCartSubtotal(BigDecimal cartSubtotal) {
        this.cartSubtotal = cartSubtotal;
    }

    public Map<Long, Long> getCartMap() {
        return cartMap;
    }

    public void setCartMap(Map<Long, Long> cartMap) {
        this.cartMap = cartMap;
    }
}
