package com.expert_soft.model.calculator;


import com.expert_soft.model.order.AbstractOrder;
import com.expert_soft.model.order.Cart;
import com.expert_soft.model.order.Order;

import java.math.BigDecimal;

public interface OrderCalculator {

   public void recalculate(Cart cart);
   public void recalculate(Order order);

}
