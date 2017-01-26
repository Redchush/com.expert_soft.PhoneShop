package com.expert_soft.util;


import com.expert_soft.model.OrderItem;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MapFactory {

    public ConcurrentMap<Long, OrderItem> createConcurrent(){
        return new ConcurrentHashMap<>();
    }

    public ConcurrentMap<Long, OrderItem> createConcurrent(Map<Long, OrderItem> map){
        return new ConcurrentHashMap<>(map);
    }
}
