package com.expert_soft.controller;


import com.expert_soft.service.OrderService;
import com.expert_soft.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class OrderController {

    private OrderService service;

    @Autowired
    public void setService(OrderService service) {
        this.service = service;
    }

}
