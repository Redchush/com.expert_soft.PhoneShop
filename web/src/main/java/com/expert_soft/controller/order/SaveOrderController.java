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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static com.expert_soft.controller.ServletConstants.CART_ATTR;

@Controller("saveOrderController")
public class SaveOrderController {

    private static final Logger LOGGER = Logger.getLogger(SaveOrderController.class);

    private OrderService orderService;

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value = "/saveOrder", method = RequestMethod.POST)
    public String saveOrder(@ModelAttribute(CART_ATTR) Cart cart,
                            @Validated(G_Order.Info.class) @Valid @ModelAttribute("userInfo") UserInfo info,
                            BindingResult validation,
                            ModelMap model,
                            RedirectAttributes redirectAttrs,
                            SessionStatus status){
        if (validation.hasErrors()){
            LOGGER.debug("Invalid user info :" + info);
            model.addAttribute("userInfo", info);
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
