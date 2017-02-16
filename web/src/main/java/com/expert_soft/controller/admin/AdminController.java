package com.expert_soft.controller.admin;

import com.expert_soft.controller.ServletConstants;
import com.expert_soft.model.order.Order;
import com.expert_soft.model.order.OrderStatus;
import com.expert_soft.service.OrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Locale;

@Controller("adminController")
public class AdminController implements MessageSourceAware{

    private static final Logger LOGGER = Logger.getLogger(AdminController.class);

    private OrderService orderService;
    private MessageSource messageSource;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView admin(){
        List<Order> all = orderService.findAll();
        return new ModelAndView("adminMain", "orders", all);
    }

    @RequestMapping(value = "/update_order", method = RequestMethod.POST)
    public String updateOrder(Locale locale,
                              @RequestParam(value = "orderKey") Long key,
                              @RequestParam(value = "status") String status,
                              RedirectAttributes redirectAttrs){
        OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
        orderService.changeStatus(key, orderStatus);
        String answer = messageSource.getMessage("order.action.update.success",
                new Object[]{key, status},
                locale);
        redirectAttrs.addFlashAttribute(ServletConstants.TEMP_MSG, answer);
        return "redirect:admin";
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}