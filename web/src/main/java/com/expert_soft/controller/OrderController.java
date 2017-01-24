package com.expert_soft.controller;


import com.expert_soft.model.Cart;
import com.expert_soft.model.Order;
import com.expert_soft.model.UserInfo;
import com.expert_soft.service.OrderService;
import com.expert_soft.validator.group.G_Order;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.expert_soft.model.ServletConstants.CART_ATTR;

@Controller
@SessionAttributes(CART_ATTR)
public class OrderController {

    private static final Logger LOGGER = Logger.getLogger(OrderController.class);

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public ModelAndView order(@ModelAttribute("cart") Cart cart){
        Order order = orderService.buildOrder(cart, true);
        Map<String, Object> map = new HashMap<>();
        map.put("order", order);
        map.put("userInfo", new UserInfo());
        return new ModelAndView("order", map );
    }

    @RequestMapping(value = "/doOrder", method = RequestMethod.POST)
    public String order(@ModelAttribute(CART_ATTR) Cart cart,
                        @Validated(G_Order.Info.class) @ModelAttribute("userInfo") UserInfo info,
                        BindingResult validation,
                        ModelMap model,
                        RedirectAttributes redirectAttrs,
                        SessionStatus status){
        if (validation.hasErrors()){
            model.addAttribute("userInfo", info);
            return "order";
        }

        Order order = orderService.buildOrder(cart, info, false);
        Long orderId = orderService.saveOrder(order);
        status.setComplete();
        redirectAttrs.addAttribute("orderId", orderId);
        return "redirect:order/confirm";
    }


    @RequestMapping(value = "/order/confirm", method = RequestMethod.GET)
    public ModelAndView confirmOrder( @RequestAttribute("orderId") Long orderId){
        Order order = orderService.getOrder(orderId);
        return new ModelAndView("orderConfirm", "order", order);
    }


    @ExceptionHandler(HttpSessionRequiredException.class)
    public ModelAndView orderWithoutCart(HttpServletRequest req, HttpSessionRequiredException e){
        LOGGER.error("Somebody try to access order without filling cart ",e);
        ModelAndView mav = new ModelAndView();
        mav.addObject("url", req.getRequestURL());
        mav.addObject("object", " you cart ");
        mav.addObject("For making order you must collect a cart");
        mav.setViewName("/error/productNotFound");
        return mav;
    }

}
