package com.expert_soft.controller.cart;


import com.expert_soft.exception.service.ajax.AjaxException;
import com.expert_soft.model.OrderItem;
import com.expert_soft.model.order.Cart;
import com.expert_soft.model.result.ValidationResult;
import com.expert_soft.service.ResponseService;
import com.expert_soft.service.CartService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

import static com.expert_soft.controller.ServletConstants.*;
import static java.lang.String.format;

@Controller("ajaxAddToCartController")
@SessionAttributes({CART_ATTR})
public class AjaxAddToCartController {

    private static final Logger LOGGER = Logger.getLogger(AjaxAddToCartController.class);

    private CartService cartService;
    private ResponseService responseService;
    @Autowired
    private Cart cart;

    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    public void setResponseService(ResponseService responseService) {
        this.responseService = responseService;
    }

    public void setCart(Cart cart) {this.cart = cart;}

    @RequestMapping(value = "/add_to_cart",
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.TEXT_PLAIN_VALUE},
            method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<String> addToCart(ModelMap model,
                                     @RequestParam(PHONE_ID_TO_ADD) Long phoneId,
                                     @RequestParam(QUANTITY_PARAM) Integer quantity){
        ValidationResult<OrderItem> validationResult = cartService.addToCart(cart, phoneId, quantity);
        if (validationResult.isSuccess()) {
            model.put(CART_ATTR, cart);
            LOGGER.debug("cart changed: " + cart);
            String body = responseService.buildJsonSuccess(cart, cartService.getPhone(cart, phoneId).getModel());
            return new ResponseEntity<>(body, HttpStatus.OK);
        } else {
            return ajaxItemViolation(validationResult);
        }
    }

    private @ResponseBody ResponseEntity<String> ajaxItemViolation(ValidationResult<OrderItem> result){
        LOGGER.debug("Violation occured: " + result.getViolations());
        String constrainMsg = result.getViolations()
                                    .iterator()
                                    .next().getMessage();
        String body = responseService.buildFail(constrainMsg, result.getInfoObject());
        return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
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
        LOGGER.error(String.format("Request: %s%s raised %s", req.getRequestURL(),
                req.getQueryString(), e));
        return responseService.buildFailUnexpected();
    }


}
