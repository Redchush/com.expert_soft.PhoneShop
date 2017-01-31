package com.expert_soft.service.impl;


import com.expert_soft.service.DeliveryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Value("${delivery.price}")
    private BigDecimal delivery;

    @Override
    public BigDecimal getDeliveryPrice() {
        return delivery;
    }
}
