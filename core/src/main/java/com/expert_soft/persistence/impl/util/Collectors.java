package com.expert_soft.persistence.impl.util;


import com.expert_soft.model.Order;
import com.expert_soft.model.OrderItem;
import com.expert_soft.model.Phone;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * util class encapsulate classes which deal with assembling model entities from ResultSet,
 * such as mappers, extractors and other classes from Spring JDBC API
 *
 */
public class Collectors {

    public static class PhoneRowMapper implements RowMapper<Phone> {

        public Phone mapRow(ResultSet rs, int rowNum) throws SQLException {
            Phone phone = new Phone();
            phone.setKey(rs.getLong(1));
            phone.setModel(rs.getString("model"));
            phone.setPrice(rs.getBigDecimal("price"));
            return phone;
        }
    }

    public static class OrderRowMapper implements RowMapper<Order>{

        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {

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


    public static class SingleOrderResultSetExtractor implements ResultSetExtractor<Order> {

        private RowMapper<Phone> phoneRowMapper;
        private RowMapper<Order> orderRowMapper;

        public SingleOrderResultSetExtractor() {
            super();
        }

        public SingleOrderResultSetExtractor(RowMapper<Phone> phoneRowMapper,
                                             RowMapper<Order>  orderRowMapper) {
            super();
            this.phoneRowMapper = phoneRowMapper;
            this.orderRowMapper = orderRowMapper;

        }

        @Override
        public Order extractData(ResultSet rs) throws SQLException, DataAccessException {

            Map<Long, OrderItem> items = new HashMap<>();
            OrderItem orderItem = null;
            Order order = null;
            while (rs.next()) {

                if(order == null) {
                   order = orderRowMapper.mapRow(rs, rs.getRow());
                }

                Long itemId = rs.getLong("order_items.id");
                orderItem = items.get(itemId);

                if (orderItem == null) {
                    orderItem = new OrderItem();
                    orderItem.setKey(itemId);
                    Phone phone = phoneRowMapper.mapRow(rs, rs.getRow());
                    orderItem.setPhone(phone);
                    items.put(itemId, orderItem);
                }
            }
            return order;
        }
    }
}
