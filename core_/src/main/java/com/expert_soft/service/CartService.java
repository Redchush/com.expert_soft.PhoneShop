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
import java.util.List;

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

    Cart deleteFromCart(Cart cart, Long[] phoneIdArray);

    /**
     * @param cart - currentCart
     * @param phoneId - phoneId, which quantity mentioned to be update
     * @param newQuantity - new quantity to be set in OrderItem connected to Phone with this id
     * @return OrderItem - new orderItem, that bound to this phoneId
     */
    OrderItem changeQuantity(Cart cart, Long phoneId, Integer newQuantity);

    void changeQuantity(Cart cart, List<OrderItem> changes);

    BigDecimal calculateAndSetSubtotal(Cart cart);

    Cart calculateAndSetSize(Cart cart);

    /**
     * check all data in persistence and calculate all data
     * @param cart
     */
    void deeplyCheckCart(Cart cart);

    @Validated(G_Cart.Item.class)
    @Valid
    OrderItem createNewOrderItem(Phone phone, Integer quantity);
}
