package com.expert_soft.service;


import com.expert_soft.model.Phone;
import com.expert_soft.model.order.Cart;
import com.expert_soft.model.order.OrderItem;
import com.expert_soft.model.result.ValidationResult;

public interface CartService {

    Cart getCart();

    boolean isCartEmpty();

    int getCartSize();

    Phone getPhone(Long phoneId);
    /**
     * Add to cart's phone quantity and validate result OrderItem
     * @param phone - phone to be saved
     * @param quantity - quantity of Phones
     * @return whether cart contains same phone
     */
    ValidationResult<OrderItem> addToCart(Phone phone,
                                          Integer quantity);

    /**
     * Add to cart's phone quantity and validate result OrderItem
     * If phone with same key present in cart, get it from cart,
     * otherwise get it from persistence
     * @param phoneKey - phone key to be saved in cart
     * @param quantity - quantity of Phones
     * @return whether cart contains same phone
     * @throws NullPointerException - if such phone not exist in database
     */
    ValidationResult<OrderItem> addToCart(Long phoneKey, Integer quantity)
            throws NullPointerException;

    OrderItem deleteFromCart(Long phoneId);

    /**
     * Remove all phones from cart listed in phoneKeys;
     * @param phoneKeys - array of phone keys, which
     * @return changed argument cart;
     */
    Cart deleteFromCart(Long[] phoneKeys);

    /**
     * Set new quantity from <strong>change</strong> parameter
     * for item in cart with same phone key as in <strong>change</strong> parameter.
     * Method doesn't apply another changes apart from quantity.
     * @param phoneKey - indicator of phone to be updated
     * @param newQuantity - quantity to be set against phone
     * @return OrderItem - new orderItem, that bound to this phoneId
     */
    OrderItem updatePhoneQuantity(Long phoneKey, Integer newQuantity);


}
