package com.expert_soft.controller;


import com.expert_soft.model.CartItemsContainer;
import com.expert_soft.model.order.Cart;
import com.expert_soft.service.OrderService;
import com.expert_soft.validator.group.G_Cart;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;

import static com.expert_soft.controller.ServletConstants.*;

@Controller("cartController")
@SessionAttributes ({CART_ATTR})
public class CartController {

    private static final Logger LOGGER = Logger.getLogger(CartController.class);

    private OrderService orderService;
    private Cart cart;

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setCart(Cart cart) {this.cart = cart;}

    @RequestMapping( value = "/cart", method = RequestMethod.GET)
    public ModelAndView cart(ModelMap model){
        Cart cart = (Cart) model.get(CART_ATTR);
        if (cart == null || cart.getOrderItems().isEmpty()){
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
                             @RequestParam(value = PHONE_TO_DELETE, required = false)
                                         Long[] phoneIds,
                             RedirectAttributes redirectAttributes){
        if (result.hasErrors()){
            model.addAttribute(MSG_CODE, MsgCodes.FAIL_UPDATE);
            LOGGER.debug("invalid lightCart: " + lightCart);
            return "fullCart";
        }
        LOGGER.debug(String.format("Changes: %s\nDelete ids:%s",
                Arrays.asList(lightCart.getItems()), Arrays.toString(phoneIds)));
        orderService.updatePhoneQuantity(cart, lightCart.getItems());
        orderService.deleteFromCart(cart, phoneIds);
        model.addAttribute(CART_ATTR, cart);
        redirectAttributes.addFlashAttribute(MSG_CODE, MsgCodes.SUCCESS_UPDATE);
        LOGGER.debug("Result cart : " + model.get(CART_ATTR));
        return "redirect:cart";
    }
}
