package com.expert_soft.persistence;




import com.expert_soft.model.Order;

import java.util.List;

public interface OrderDao {


    Order getOrder(Long key);
    void saveOrder(Order order);
    List<Order> findAll();

}
