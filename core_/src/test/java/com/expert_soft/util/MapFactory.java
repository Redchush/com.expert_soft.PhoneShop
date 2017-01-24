package com.expert_soft.util;


import com.expert_soft.model.OrderItem;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MapFactory {

    public static ConcurrentMap<Long, OrderItem> createConcurrent(){
        return new ConcurrentHashMap<>();
    }
}
