package com.expert_soft.controller.cart;


import com.expert_soft.model.CartItemsContainer;
import com.expert_soft.model.order.Cart;
import com.expert_soft.service.CartService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import static com.expert_soft.controller.ServletConstants.CART_ATTR;
import static com.expert_soft.controller.ServletConstants.CART_ITEMS;

@Controller("cartController")
@SessionAttributes ({CART_ATTR})
public class CartController {

    private static final Logger LOGGER = Logger.getLogger(CartController.class);

    private CartService cartService;
    @Autowired   private Cart cart;

    public void setCartService(CartService orderService) {
        this.cartService = orderService;
    }

    public void setCart(Cart cart) {this.cart = cart;}

    @RequestMapping( value = "/cart", method = RequestMethod.GET)
    public ModelAndView cart(ModelMap model){
        Cart cart = (Cart) model.get(CART_ATTR);
        if (cartService.isCartEmpty(cart)){
            return new ModelAndView("emptyCart");
        }
        ModelAndView fullCart = new ModelAndView("fullCart",
                CART_ITEMS,
                new CartItemsContainer(cartService.getCartSize(cart)));
        fullCart.addObject(CART_ATTR, cart);
        return fullCart;
    }


}
