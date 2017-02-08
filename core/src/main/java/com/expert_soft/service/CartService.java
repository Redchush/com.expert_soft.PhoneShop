package com.expert_soft.service;


import com.expert_soft.model.OrderItem;
import com.expert_soft.model.Phone;
import com.expert_soft.model.order.Cart;
import com.expert_soft.model.result.ValidationResult;
import com.expert_soft.validator.group.G_Cart;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

public interface CartService {

    Cart getCart();

    boolean isCartEmpty(Cart cart);

    int getCartSize(Cart cart);

    Phone getPhone(Cart cart, Long phoneId);
    /**
     * Add to cart's phone quantity and validate result OrderItem
     * @param cart - current cart
     * @param phone - phone to be saved
     * @param quantity - quantity of Phones
     * @return whether cart contains same phone
     */
    ValidationResult<OrderItem> addToCart(Cart cart,
                                          Phone phone,
                                          Integer quantity);

    /**
     * Add to cart's phone quantity and validate result OrderItem
     * If phone with same key present in cart, get it from cart,
     * otherwise get it from persistence
     * @param cart - current cart
     * @param phoneKey - phone key to be saved in cart
     * @param quantity - quantity of Phones
     * @return whether cart contains same phone
     * @throws NullPointerException - if such phone not exist in database
     */
    ValidationResult<OrderItem> addToCart(Cart cart, Long phoneKey, Integer quantity) throws NullPointerException;

    OrderItem deleteFromCart(Cart cart, Long phoneId);

    /**
     * Remove all phones from cart listed in phoneKeys;
     * @param cart - current cart
     * @param phoneKeys - array of phone keys, which
     * @return changed argument cart;
     */
    Cart deleteFromCart(Cart cart, Long[] phoneKeys);

    /**
     * Set new quantity from <strong>change</strong> parameter
     * for item in cart with same phone key as in <strong>change</strong> parameter.
     * Method doesn't apply another changes apart from quantity.
     * @param cart - currentCart
     * @param change - lightweight OrderItem which contains only Phone with key and quantity
     *               phoneId, which quantity mentioned to be update
     * @return OrderItem - new orderItem, that bound to this phoneId
     */
    @Validated(G_Cart.Item.class)
    OrderItem updatePhoneQuantity(Cart cart, @Valid OrderItem change);

    /**
     * Apply only quantity changes.
     * @param cart - current cart
     * @param changes - array, each element of which contains quantitiy changes
     * @return changed cart
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

}
