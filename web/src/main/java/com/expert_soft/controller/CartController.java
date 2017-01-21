package com.expert_soft.controller;


import com.expert_soft.helper.JsonHelper;
import com.expert_soft.helper.impl.JsonHelperImpl;
import com.expert_soft.model.AjaxResponseCart;
import com.expert_soft.model.Cart;
import com.expert_soft.model.Phone;
import com.expert_soft.service.CartService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

import static com.expert_soft.model.ServletConstants.*;

@Controller
@SessionAttributes ({CART_ATTR, CART_CURRICULUM_ATTR})
public class CartController {

    private static final Logger LOGGER = Logger.getLogger(CartController.class);

    private CartService cartService;
    private JsonHelper jsonHelper;

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }
    @Autowired
    public void setJsonHelper(JsonHelperImpl jsonHelper) {
        this.jsonHelper = jsonHelper;
    }

    @RequestMapping( value = "/cart", method = RequestMethod.GET)
    public ModelAndView cart(ModelMap model){
        Object cart = model.get(CART_ATTR);
        if (cart == null){
            return new ModelAndView("emptyCart");
        }
        return new ModelAndView("cart");
    }

    @RequestMapping(value = "/add_to_cart",
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
                    method = RequestMethod.GET)
    public @ResponseBody String addToCart(ModelMap model,
                               @RequestParam(PHONE_TO_DELETE) Long phoneId,
                               @RequestParam(QUANTITY_PARAM) Integer quantity){
        LOGGER.debug("catch ajax changed: modelMap " + model);

        Cart cart = getCurrentCart(model);
        Phone phone = cartService.addToCart(cart, phoneId, quantity);
        model.put(CART_ATTR, cart);
        com.expert_soft.model.excluded.CartCurriculum curriculum = cartService.buildCurriculum(cart);
        model.put(CART_CURRICULUM_ATTR, curriculum);

        AjaxResponseCart response = new AjaxResponseCart();
        response.setResult(curriculum);
        response.setCode("200");
        response.setMsg("Model of phone " + phone.getModel() + " successfully added to cart");

        LOGGER.debug("cart changed: " + cart.toString() );

        String result = "";
        try {
           result= jsonHelper.write(response);
        } catch (IOException e) {
           LOGGER.debug("Fail to write " + response.getResult());
        }
        return result;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String changeQuantity(ModelMap model,
                                 @ModelAttribute(CART_ATTR) Cart cart,
                                 @RequestParam(PHONE_TO_DELETE) Long[] phoneArray,
                                 BindingResult result){


        cartService.deleteFromCart(cart, phoneArray);
        com.expert_soft.model.excluded.CartCurriculum curriculum = cartService.buildCurriculum(cart);
        model.addAttribute(CART_CURRICULUM_ATTR, curriculum);
        return "redirect:cart";
    }

    private Cart getCurrentCart(ModelMap modelMap){
        Cart cart = (Cart) modelMap.get("cart");
        if (cart == null){
            cart = Cart.EMPTY_CART;
        }
        return cart;
    }


}
