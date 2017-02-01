package com.expert_soft.controller;


import com.expert_soft.model.CartItemsContainer;
import com.expert_soft.model.order.Cart;
import com.expert_soft.service.OrderService;
import com.expert_soft.validator.group.G_Cart;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Set;

import static com.expert_soft.controller.ServletConstants.*;

@Controller
@SessionAttributes ({CART_ATTR})
public class CartController {

    private static final Logger LOGGER = Logger.getLogger(CartController.class);

    private OrderService cartService;

    @Autowired
    public void setCartService(OrderService cartService) {
        this.cartService = cartService;
    }

    @RequestMapping( value = "/cart", method = RequestMethod.GET)
    public ModelAndView cart(ModelMap model){
        Cart cart = (Cart) model.get(CART_ATTR);
        if (cart == null){
            return new ModelAndView("emptyCart");
        }
        ModelAndView fullCart = new ModelAndView("fullCart",
                CART_ITEMS,
                new CartItemsContainer(cart.getOrderItems().size()));
        fullCart.addObject(CART_ATTR, cart);
        return fullCart;
    }

    /**
     * 1) Validate new items
     * 2) Set (not add) new quantities to corresponded phone in the cart
     * 3) Delete all phones from cart listed in phoneIds parameter
     */
    @RequestMapping(value = "/update_cart", method = RequestMethod.POST)
    public String updateCart(ModelMap model,
                             @Validated(G_Cart.Item.class)
                             @ModelAttribute(CART_ITEMS) CartItemsContainer lightCart,
                             BindingResult result,
                             @ModelAttribute(CART_ATTR) Cart cart,
                             @RequestParam(value = PHONE_TO_DELETE, required = false) Long[] phoneIds,
                             RedirectAttributes redirectAttributes){
        if (result.hasErrors()){
            model.addAttribute(MSG_CODE, MsgCodes.FAIL_UPDATE);
           LOGGER.debug("invalid lightCart: " + lightCart);
            return "fullCart";
        }
        LOGGER.debug("Changes: " + Arrays.asList(lightCart.getItems()) + "" +
                ". Delete: " + Arrays.toString(phoneIds));
        cartService.updatePhoneQuantity(cart, lightCart.getItems());
        cartService.deleteFromCart(cart, phoneIds);
        redirectAttributes.addFlashAttribute(MSG_CODE, MsgCodes.SUCCESS_UPDATE);
        return "redirect:cart";
    }

//    @ExceptionHandler(ConstraintViolationException.class)
//    @ResponseStatus(HttpStatus.OK)
//    public String invalidCartUpdate(ConstraintViolationException e){
//        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
//        BindingResult result = new B
//
//    }

}
