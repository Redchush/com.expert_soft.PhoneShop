package com.expert_soft.controller.cart;


import com.expert_soft.exception.service.ajax.AjaxException;
import com.expert_soft.model.order.Cart;
import com.expert_soft.model.order.OrderItem;
import com.expert_soft.model.result.ValidationResult;
import com.expert_soft.service.CartResponseService;
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
    private CartResponseService cartResponseService;


    @RequestMapping(value = "/add_to_cart",
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.TEXT_PLAIN_VALUE},
            method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<String> addToCart(ModelMap model,
                                                          @RequestParam(PHONE_ID_TO_ADD) Long phoneId,
                                                          @RequestParam(QUANTITY_PARAM) Integer quantity){
        ValidationResult<OrderItem> validationResult = cartService.addToCart(phoneId, quantity);
        if (validationResult.isSuccess()) {
            Cart cart = cartService.getCart();

            model.put(CART_ATTR, cart);
            LOGGER.debug("cart changed: " + cart);
            String body = cartResponseService.buildJsonSuccess(cart, cartService.getPhone(phoneId).getModel());
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
        String body = cartResponseService.buildFail(constrainMsg, result.getInfoObject());
        return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
    }


    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({NumberFormatException.class, MethodArgumentTypeMismatchException.class,
            NullPointerException.class})
    public @ResponseBody String ajaxNumberFormatViolation(HttpServletRequest req, Exception e){
        LOGGER.debug(format("Number format exception for input '%s' and '%s'.",
                req.getParameter(PHONE_ID_TO_ADD),
                req.getParameter(QUANTITY_PARAM)), e);
        return cartResponseService.buildInvalidFormat();
    }

    @ExceptionHandler(value = AjaxException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String ajaxIO(AjaxException e){
        LOGGER.error("Ajax response can't be sent to client", e);
        return cartResponseService.buildFailToWrite();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public @ResponseBody String ajaxUnexpectedError(HttpServletRequest req, Exception e){
        LOGGER.error(String.format("Request: %s%s raised %s", req.getRequestURL(),
                req.getQueryString(), e));
        return cartResponseService.buildFailUnexpected();
    }

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @Autowired
    public void setCartResponseService(CartResponseService cartResponseService) {
        this.cartResponseService = cartResponseService;
    }


}
