package com.expert_soft.persistence.impl.util.collector;


import com.expert_soft.model.Order;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderCollector implements ResultSetCollector<Order> {

    @Override
    public Order collectEntityFromObject(ResultSet rs) throws SQLException {

        Order order = new Order();

        order.setKey(rs.getLong("orders.id"));
        order.setFirstName(rs.getString("orders.first_name"));
        order.setLastName(rs.getString("orders.last_name"));

        order.setDeliveryAddress(rs.getString("orders.delivery_address"));
        order.setContactPhoneNo(rs.getString("orders.contact_phone_no"));
        order.setDeliveryPrice(rs.getBigDecimal("orders.delivery_price"));

        return order;
    }
}
