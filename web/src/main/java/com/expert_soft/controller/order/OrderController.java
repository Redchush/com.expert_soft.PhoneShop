package com.expert_soft.controller.order;


import com.expert_soft.model.order.Cart;
import com.expert_soft.model.order.Order;
import com.expert_soft.model.order.UserInfo;
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
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static com.expert_soft.controller.ServletConstants.CART_ATTR;

@Controller("orderController")
@SessionAttributes(CART_ATTR)
public class OrderController {

    private static final Logger LOGGER = Logger.getLogger(OrderController.class);

    private OrderService orderService;

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public ModelAndView order(@ModelAttribute("cart") Cart cart){
        Order order = orderService.buildOrder(cart, new UserInfo(), true);
        Map<String, Object> map = new HashMap<>();
        map.put("order", order);
        map.put("userInfo", new UserInfo());
        return new ModelAndView("newOrder", map );
    }


    @ExceptionHandler(HttpSessionRequiredException.class)
    public ModelAndView orderWithoutCart(HttpServletRequest req, HttpSessionRequiredException e){
        LOGGER.error("Somebody try to access order without filling cart ",e);
        ModelAndView mav = new ModelAndView();
        mav.addObject("url", req.getRequestURL());
        mav.addObject("object", " you cart ");
        mav.addObject("For making order you must collect a cart");
        mav.setViewName("/error/notFound");
        return mav;
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public String saveOrder(@ModelAttribute(CART_ATTR) Cart cart,
                            @Validated(G_Order.Info.class) @Valid
                            @ModelAttribute("userInfo") UserInfo info,
                            BindingResult validation,
                            ModelMap model,
                            RedirectAttributes redirectAttrs,
                            SessionStatus status){
        if (validation.hasErrors()){
            Order order = orderService.buildOrder(cart, new UserInfo(), true);
            model.put("order", order);
            LOGGER.debug("Invalid user info :" + info + "Current cart " + cart);
            return "newOrder";
        }
        Order order = orderService.buildOrder(cart, info, false);
        Long orderId = orderService.saveOrder(order);
        LOGGER.debug("Order successfully saved: " + order);
        status.setComplete();
        redirectAttrs.addAttribute("orderId", orderId);
        return "redirect:order/{orderId}";
    }


    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

}
