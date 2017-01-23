package com.expert_soft.controller;


import com.expert_soft.exception.service.ajax.AjaxException;
import com.expert_soft.model.Cart;
import com.expert_soft.model.Phone;
import com.expert_soft.service.AjaxResponseService;
import com.expert_soft.service.CartService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

import static com.expert_soft.model.ServletConstants.*;

@Controller
@SessionAttributes({CART_ATTR})
public class AjaxCartController {

    private static final Logger LOGGER = Logger.getLogger(CartController.class);

    private CartService cartService;
    private AjaxResponseService responseService;

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @Autowired
    public void setResponseService(AjaxResponseService responseService) {
        this.responseService = responseService;
    }

    @RequestMapping(value = "/add_to_cart",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            method = RequestMethod.GET)
    public @ResponseBody
    String addToCart(ModelMap model,
                     @RequestParam(PHONE_TO_DELETE) Long phoneId,
                     @RequestParam(QUANTITY_PARAM) Integer quantity){
        LOGGER.debug("catch ajax changed: modelMap " + model);

        Cart cart = getCurrentCart(model);
        Phone phone = cartService.deepAddToCart(cart, phoneId, quantity);
        cartService.calculateAndSetSubtotal(cart);
        cartService.calculateAndSetSize(cart);

        model.put(CART_ATTR, cart);
        LOGGER.debug("cart changed: " + cart.toString());
        return responseService.buildSuccessAndWrite(cart, phone.getModel());
    }

    @ExceptionHandler(value = AjaxException.class)
    public ResponseEntity<String> ajaxIO(AjaxException e){
        String s = responseService.buildFailToWrite();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity<String>(s, headers, HttpStatus.INTERNAL_SERVER_ERROR);
}

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> orderAjaxItemViolation(ConstraintViolationException e){
        String message = e.getConstraintViolations().iterator()
                          .next().getMessage();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity<String>(message, headers, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private Cart getCurrentCart(ModelMap modelMap){
        Cart cart = (Cart) modelMap.get("cart");
        if (cart == null){
            cart = new Cart();
        }
        return cart;
    }
}
