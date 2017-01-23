package com.expert_soft.helper.impl;


import org.codehaus.jackson.annotate.JsonIgnore;

public abstract class CartMixIn {

    @JsonIgnore
    abstract int getItemsMap();
}
