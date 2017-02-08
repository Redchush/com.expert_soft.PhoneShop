package com.expert_soft.controller.order;


import com.expert_soft.model.UserInfo;
import com.expert_soft.model.order.Cart;
import com.expert_soft.model.order.Order;
import com.expert_soft.service.OrderService;
import com.expert_soft.validator.group.G_Order;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static com.expert_soft.controller.ServletConstants.CART_ATTR;

@Controller("saveOrderController")
@SessionAttributes(CART_ATTR)
public class SaveOrderController {

    private static final Logger LOGGER = Logger.getLogger(SaveOrderController.class);

    private OrderService orderService;

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
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

}
