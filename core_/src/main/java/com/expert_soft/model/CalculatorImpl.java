package com.expert_soft.model;


import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collection;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CalculatorImpl implements Calculator {

    @Override
    public BigDecimal calculateSubtotal(Collection<OrderItem> items){
        BigDecimal result = new BigDecimal(0);
        for (OrderItem item : items){
            item.recalculateSubtotal();
            result = result.add(item.recalculateSubtotal());
        }
        return result;
    }
}
