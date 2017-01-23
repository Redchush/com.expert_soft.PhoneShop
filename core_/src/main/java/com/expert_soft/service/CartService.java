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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.executable.ExecutableType;
import javax.validation.executable.ValidateOnExecution;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.util.List;

@Validated(G_Cart.Item.class)
@ValidateOnExecution(type = ExecutableType.ALL)
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

    @Validated(G_Cart.Item.class)
    boolean addToCart(Cart cart, @Valid OrderItem item);

    /**
     * Add to cart phone by id with retrieving this phone from persistence
     * @param cart
     * @param phoneId
     * @param quantity
     * @return
     */

    @Validated({G_Cart.Item.class, Default.class})
    @ValidateOnExecution(type = ExecutableType.ALL)
    Phone deepAddToCart(Cart cart,
                        @NotNull Long phoneId,
                        @Min(value = 1, groups = G_Cart.Item.class,
                                message = "{orderItem.quantity.min}")
                        @Max(value = 10, groups = G_Cart.Item.class,
                                message = "{orderItem.quantity.max}")
                        @NotNull (groups = G_Cart.Item.class, message = "{orderItem.quantity.notNull}")
                                Integer quantity);

    OrderItem deleteFromCart(Cart cart, Long phoneId);

    Cart deleteFromCart(Cart cart, Long[] phoneIdArray);

    /**
     * @param cart - currentCart
     * @param phoneId - phoneId, which quantity mentioned to be update
     * @param newQuantity - new quantity to be set in OrderItem connected to Phone with this id
     * @return OrderItem
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
    @ValidateOnExecution(type = ExecutableType.ALL)
    @Valid
    OrderItem createNewOrderItem(Phone phone, @Max(10) Integer quantity);
}
