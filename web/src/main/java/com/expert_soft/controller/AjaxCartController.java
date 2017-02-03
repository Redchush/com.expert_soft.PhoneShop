package com.expert_soft.controller;


import com.expert_soft.exception.service.ajax.AjaxException;
import com.expert_soft.model.OrderItem;
import com.expert_soft.model.order.Cart;
import com.expert_soft.service.AjaxResponseService;
import com.expert_soft.service.OrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import static com.expert_soft.controller.ServletConstants.*;
import static java.lang.String.format;

@Controller("ajaxCartController")
@SessionAttributes({CART_ATTR})
public class AjaxCartController {

    private static final Logger LOGGER = Logger.getLogger(AjaxCartController.class);

    private OrderService orderService;
    private AjaxResponseService responseService;
    private Cart cart;

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public void setResponseService(AjaxResponseService responseService) {
        this.responseService = responseService;
    }

    @Autowired
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @RequestMapping(value = "/add_to_cart",
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.TEXT_PLAIN_VALUE},
            method = RequestMethod.GET)
    public @ResponseBody
    String addToCart(ModelMap model,
                     @RequestParam(PHONE_ID_TO_ADD) Long phoneId,
                     @RequestParam(QUANTITY_PARAM) Integer quantity){
        LOGGER.debug("catch ajax changed: modelMap " + model);
        OrderItem item = orderService.addToCart(cart, phoneId, quantity);
        model.put(CART_ATTR, cart);
        LOGGER.debug("cart changed: " + cart);
        return responseService.buildAjaxSuccess(cart, item.getPhone().getModel());
    }


    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ConstraintViolationException.class)
    public @ResponseBody String ajaxItemViolation(ConstraintViolationException e){
        LOGGER.debug("Violation occured: " + e.getConstraintViolations());
        String constrainMsg = e.getConstraintViolations().iterator()
                               .next().getMessage();
        return responseService.buildFail(constrainMsg);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({NumberFormatException.class, MethodArgumentTypeMismatchException.class,
                       NullPointerException.class})
    public @ResponseBody String ajaxNumberFormatViolation(HttpServletRequest req, Exception e){
        LOGGER.debug(format("Number format exception for input '%s' and '%s'.",
                req.getParameter(PHONE_ID_TO_ADD),
                req.getParameter(QUANTITY_PARAM)), e);
        return responseService.buildInvalidFormat();
    }

    @ExceptionHandler(value = AjaxException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String ajaxIO(AjaxException e){
        LOGGER.error("Ajax response can't be sent to client", e);
        return responseService.buildFailToWrite();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public @ResponseBody String ajaxUnexpectedError(HttpServletRequest req, Exception e){
        LOGGER.error(String.format("Request: %s%s raised ", req.getRequestURL(),
                req.getQueryString(), e), e);
        return responseService.buildFailUnexpected();
    }

//    private Cart getCurrentCart(ModelMap modelMap){
//        Cart cart = (Cart) modelMap.get("cart");
//        if (cart == null){
//            cart = new Cart();
//        }
//        return cart;
//    }

    private HttpHeaders getJsonHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return headers;
    }
}
