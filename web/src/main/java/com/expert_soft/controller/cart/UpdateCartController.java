package com.expert_soft.controller.cart;


import com.expert_soft.model.CartItemsContainer;
import com.expert_soft.model.order.Cart;
import com.expert_soft.service.CartService;
import com.expert_soft.validator.group.G_Cart;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;

import static com.expert_soft.controller.ServletConstants.*;

@Controller("updateCartController")
@SessionAttributes({CART_ATTR})
public class UpdateCartController {
    private static final Logger LOGGER = Logger.getLogger(UpdateCartController.class);

    private CartService cartService;
    @Autowired
    private Cart cart;

    public void setCart(Cart cart) {this.cart = cart;}

    public void setCartService(CartService cartService) {
        this.cartService = cartService;
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
        cartService.updatePhoneQuantity(cart, lightCart.getItems());
        cartService.deleteFromCart(cart, phoneIds);
        model.addAttribute(CART_ATTR, cart);
        redirectAttributes.addFlashAttribute(MSG_CODE, MsgCodes.SUCCESS_UPDATE);
        LOGGER.debug("ValidationResult cart : " + model.get(CART_ATTR));
        return "redirect:cart";
    }
}
