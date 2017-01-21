package com.expert_soft.controller;


import com.expert_soft.model.Cart;
import com.expert_soft.model.Order;
import com.expert_soft.model.UserInfo;
import com.expert_soft.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller

public class OrderController {

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public ModelAndView order(@ModelAttribute("cart") Cart cart){
        Order order = orderService.buildOrder(cart);
        Map<String, Object> map = new HashMap<>();
        map.put("order", order);
        map.put("userInfo", new UserInfo());
        return new ModelAndView("order", map );
    }

    @RequestMapping(value = "/doOrder", method = RequestMethod.POST)
    public String order(@Valid @ModelAttribute("cart") Cart cart,
                        @ModelAttribute("userInfo") UserInfo info,
                        BindingResult validation,
                        ModelMap model,
                        SessionStatus status){
        if (validation.hasErrors()){
            model.addAttribute("info", info);
            return "order";
        }
        Order order = orderService.buildOrder(cart, info);
        Long orderId = orderService.saveOrder(order);
        status.setComplete();
        return "redirect:order/confirm?orderId=" + orderId;
    }


    @RequestMapping(value = "/order/confirm", method = RequestMethod.GET)
    public ModelAndView confirmOrder( @RequestAttribute("orderId") Long orderId,
                                      @ModelAttribute("userInfo") UserInfo info){
        Order order = orderService.getOrder(orderId);
        return new ModelAndView("orderConfirm", "order", order);
    }

    public void changeQuantity(){

    }




}
