package com.expert_soft.persistence.impl.util;


import com.expert_soft.model.Order;
import com.expert_soft.model.OrderItem;
import com.expert_soft.model.Phone;
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

    public static class OrderResultSetExtractor implements ResultSetExtractor<Order> {
        @Override
        public Order extractData(ResultSet rs) throws SQLException, DataAccessException {

            Map<Long, OrderItem> items = new HashMap<>();
            OrderItem orderItem = null;
            Order order1 = null;
            while (rs.next()) {

                if(order1 == null) {
                    order1 = new Order();
                    order1.setKey(rs.getLong("orders.id"));
                    order1.setFirstName(rs.getString("orders.first_name"));
                    order1.setLastName(rs.getString("orders.last_name"));
                    order1.setDeliveryAddress(rs.getString("orders.delivery_address"));
                    order1.setContactPhoneNo(rs.getString("orders.contact_phone_no"));
                    order1.setDeliveryPrice(rs.getBigDecimal("orders.delivery_price"));
                }

                Long itemId = rs.getLong("order_items.id");
                orderItem = items.get(itemId);

                if (orderItem == null) {
                    orderItem = new OrderItem();
                    orderItem.setKey(itemId);

                    Phone phone = new Phone();
                    phone.setKey(rs.getLong("phones.id"));
                    phone.setPrice(rs.getBigDecimal("phones.price"));
                    phone.setModel(rs.getString("phones.model"));

                    orderItem.setPhone(phone);
                    items.put(itemId, orderItem);
                }
            }
            return order1;
        }
    }
}
