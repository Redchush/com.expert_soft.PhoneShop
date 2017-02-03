package com.expert_soft.model.calculator;


import com.expert_soft.model.OrderItem;
import com.expert_soft.model.order.AbstractOrder;
import com.expert_soft.model.order.Cart;
import com.expert_soft.model.order.Order;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("orderCalculator")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class OrderCalculatorImpl implements OrderCalculator {

    @Override
    public void recalculate(Cart cart){
        calculateAndSetSubtotal(cart);
        calculateAndSetTotal(cart);
    }

    @Override
    public void recalculate(Order order){
        calculateAndSetSubtotal(order);
        calculateAndSetTotal(order);
    }

    private BigDecimal calculateAndSetSubtotal(AbstractOrder order){
        BigDecimal result = new BigDecimal("0.00");
        for (OrderItem item : order.getOrderItems()){
            BigDecimal itemSubtotal = calculateAndSetSubtotal(item);
            result = result.add(itemSubtotal);
        }
        order.setSubtotal(result);
        return result;
    }

    private BigDecimal calculateAndSetSubtotal(OrderItem item){
        BigDecimal multiply = item.getPhone().getPrice()
                                  .multiply(new BigDecimal(item.getQuantity()));
        item.setSubtotal(multiply);
        return multiply;
    }

    private void calculateAndSetTotal(Order order){
        BigDecimal result = order.getSubtotal()
                                 .add(order.getDeliveryPrice());
        order.setTotalPrice(result);
    }

    private void calculateAndSetTotal(Cart cart){
        int result = 0;
        for (OrderItem item: cart.getOrderItems()){
            result+= item.getQuantity();
        }
        cart.setTotalPhonesCount(result);
    }


}
