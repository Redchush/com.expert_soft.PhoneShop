package com.expert_soft.service;


import com.expert_soft.model.Calculator;
import com.expert_soft.model.Cart;
import com.expert_soft.model.OrderItem;
import com.expert_soft.model.Phone;
import com.expert_soft.persistence.PhoneDao;
import com.expert_soft.validator.group.G_Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Validated(G_Cart.Item.class)
public interface CartService {

    @Autowired
    void setPhoneDao(PhoneDao phoneDao);

    @Autowired
    void setCalculator(Calculator calculator);

    /**
     * @param cart - current cart
     * @param phone - phone to be saved
     * @param quantity - quantity of Phones
     * @return whether cart contains same phone
     */
    OrderItem addToCart(Cart cart, Phone phone, Integer quantity);

    /**
     * Add to cart phone by id with retrieving this phone from persistence
     * @param cart
     * @param phoneId
     * @param quantity
     * @return
     */
    OrderItem deepAddToCart(Cart cart,
                            @NotNull(message = "{common.key}", groups = G_Cart.Item.class)
                            @Min(value = 1, message = "{common.key}", groups = G_Cart.Item.class)
                                    Long phoneId,
                            Integer quantity);

    OrderItem deleteFromCart(Cart cart, Long phoneId);

    /**
     * Remove all phones from cart listed in phoneKeys;
     * @param cart
     * @param phoneKeys - array of phone keys, which
     * @return changed argument cart;
     */
    default Cart deleteFromCart(Cart cart, Long[] phoneKeys){
        if (phoneKeys != null) {
            for (Long phoneId : phoneKeys) {
                cart.removeByPhoneKey(phoneId);
            }
        }
        return cart;
    }

    /**
     * Set new quantity from <strong>change</strong> parameter
     * for item in cart with same phone key as in <strong>change</strong> parameter.
     * Method doesn't apply another changes apart from qauntity.
     * @param cart - currentCart
     * @param change - lightweight OrderItem which contains only Phone with key and quantity
     *               phoneId, which quantity mentioned to be update
     * @return OrderItem - new orderItem, that bound to this phoneId
     */
    @Validated(G_Cart.Item.class)
    OrderItem updatePhoneQuantity(Cart cart, @Valid OrderItem change);

    /**
     * Apply only quantity changes.
     * @param cart
     * @param changes
     * @return
     */
    @Validated(G_Cart.Item.class)
    default Cart updatePhoneQuantity(Cart cart, @Valid OrderItem[] changes){
        if (changes != null){
            for (OrderItem item :changes){
                updatePhoneQuantity(cart, item);
            }
        }
        return cart;
    }

    BigDecimal calculateAndSetSubtotal(Cart cart);

    Cart calculateAndSetSize(Cart cart);

    /**
     * check all data in persistence to be sure that data is actual
     * and calculate all data
     * @param cart
     */
    void deeplyCheckCart(Cart cart);

    @Validated(G_Cart.Item.class)
    @Valid
    OrderItem createNewOrderItem(Phone phone, Integer quantity);
}
