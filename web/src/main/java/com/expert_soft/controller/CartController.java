package com.expert_soft.controller;


import com.expert_soft.service.CartService;
import com.expert_soft.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CartController {

    private CartService service;

    @Autowired
    public void setService(CartService service) {
        this.service = service;
    }



}
