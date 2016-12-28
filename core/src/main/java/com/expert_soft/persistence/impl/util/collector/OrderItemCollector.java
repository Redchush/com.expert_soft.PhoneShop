package com.expert_soft.persistence.impl.util.collector;


import com.expert_soft.model.OrderItem;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemCollector implements ResultSetCollector<OrderItem> {

    @Override
    public OrderItem collectEntityFromObject(ResultSet resultSet) throws SQLException {
        return null;
    }
}
