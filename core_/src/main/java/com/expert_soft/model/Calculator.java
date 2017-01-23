package com.expert_soft.model;


import java.math.BigDecimal;
import java.util.Collection;

public interface Calculator {

    BigDecimal calculateSubtotal(Collection<OrderItem> items);
}
