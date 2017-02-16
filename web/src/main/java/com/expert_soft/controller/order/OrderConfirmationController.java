package com.expert_soft.controller.order;

import com.expert_soft.model.order.Order;
import com.expert_soft.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller("orderConfirmationController")
public class OrderConfirmationController {

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value = "/order/{orderId}", method = RequestMethod.GET)
    public ModelAndView order(@PathVariable("orderId") Long orderId){
        Order order = orderService.getOrder(orderId);
        return new ModelAndView("orderDetails", "order", order);
    }
}
