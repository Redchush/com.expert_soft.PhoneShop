package com.expert_soft.config;


import com.expert_soft.controller.AjaxCartController;
import com.expert_soft.controller.CartController;
import com.expert_soft.controller.OrderController;
import com.expert_soft.controller.ProductController;
import com.expert_soft.helper.JsonResponsible;
import com.expert_soft.helper.impl.JsonResponsibleImpl;
import com.expert_soft.model.order.Cart;
import com.expert_soft.service.AjaxResponseService;
import com.expert_soft.service.OrderService;
import com.expert_soft.service.PhoneService;
import com.expert_soft.service.impl.AjaxResponseServiceImpl;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
@Import(ApplicationConfig.class)
@ImportResource("classpath:context/core_root-context.xml")
public class ServletConfig {

    @Autowired private OrderService orderService;
    @Autowired private PhoneService phoneService;

    @Bean
    @SessionScope
    public Cart cart(){
        return new Cart();
    }

    @Bean("jsonResponsible")
    public JsonResponsible jsonResponsible(){
        JsonResponsibleImpl jsonResponsible = new JsonResponsibleImpl();
        jsonResponsible.setMapper(new ObjectMapper());
        return jsonResponsible;
     }

    @Bean("ajaxResponseService")
    public AjaxResponseService ajaxResponseService(){
        AjaxResponseServiceImpl result = new AjaxResponseServiceImpl();
        result.setJsonResponsible(jsonResponsible());
        return result;
    }

    @Bean("cartController")
    public CartController cartController(){
        CartController controller = new CartController();
        controller.setOrderService(orderService);
        return controller;
    }

    @Bean("ajaxCartController")
    public AjaxCartController ajaxCartController(){
        AjaxCartController controller = new AjaxCartController();
        controller.setOrderService(orderService);
        controller.setResponseService(ajaxResponseService());
        return controller;
    }

    @Bean("orderController")
    public OrderController orderController(){
        OrderController controller = new OrderController();
        controller.setOrderService(orderService);
        return controller;
    }

    @Bean("productController")
    public ProductController productController(){
        ProductController controller = new ProductController();
        controller.setService(phoneService);
        return controller;
    }
}
