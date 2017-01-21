package com.expert_soft.persistence;




import com.expert_soft.model.Order;

import java.util.List;

public interface OrderDao {

    /**
     * return full-stuffed Order with one or more OrderItems
     */
    Order getOrder(Long key);
    Long saveOrder(Order order);
    List<Order> findAll();

}
