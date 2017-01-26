package com.expert_soft.controller;


import com.expert_soft.model.Cart;
import com.expert_soft.model.CartItemsContainer;
import com.expert_soft.model.OrderItem;
import com.expert_soft.service.CartService;
import com.expert_soft.validator.group.G_Cart;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;

import static com.expert_soft.controller.ServletConstants.*;

@Controller
@SessionAttributes ({CART_ATTR})
public class CartController {

    private static final Logger LOGGER = Logger.getLogger(CartController.class);

    private CartService cartService;

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @RequestMapping( value = "/cart", method = RequestMethod.GET)
    public ModelAndView cart(ModelMap model){
        Cart cart = (Cart) model.get(CART_ATTR);
        if (cart == null){
            return new ModelAndView("emptyCart");
        }
        cartService.deeplyCheckCart(cart);
        ModelAndView fullCart = new ModelAndView("fullCart",
                                                CART_ITEMS,
                                                new CartItemsContainer(cart.getItemsMap().size()));
        fullCart.addObject(CART_ATTR, cart);
        return fullCart;
    }

    @RequestMapping(value = "/update_cart", method = RequestMethod.POST)
    public String updateCart(ModelMap model,
                             @Validated(G_Cart.Item.class)
                             @ModelAttribute(CART_ITEMS) CartItemsContainer lightCart,
                             @ModelAttribute(CART_ATTR) Cart cart,
                             @RequestParam(value = PHONE_TO_DELETE, required = false) Long[] phoneIds,
                             BindingResult result){
        if (result.hasErrors()){
            model.addAttribute(CART_ITEMS, new ArrayList<OrderItem>());
        } else {
            LOGGER.debug("Changes: " + Arrays.asList(lightCart.getItems()) + "" +
                    ". Delete: " + Arrays.toString(phoneIds));
            cartService.updatePhoneQuantity(cart, lightCart.getItems());
            cartService.deleteFromCart(cart, phoneIds);
        }
        return "/cart";
    }

}
