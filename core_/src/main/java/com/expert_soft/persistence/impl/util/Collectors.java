package com.expert_soft.persistence.impl.util;


import com.expert_soft.model.Order;
import com.expert_soft.model.OrderItem;
import com.expert_soft.model.Phone;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * com.expert_soft.util class encapsulate classes which deal with assembling model entities from ResultSet,
 * such as mappers, extractors and other classes from Spring JDBC API
 *
 */
public class Collectors {

    public static class PhoneRowMapper implements RowMapper<Phone> {

        public Phone mapRow(ResultSet rs, int rowNum) throws SQLException {
            Phone phone = new Phone();
            phone.setKey(rs.getLong("phones.id"));
            phone.setModel(rs.getString("phones.model"));
            BigDecimal price = rs.getBigDecimal("phones.price");

            phone.setPrice(DataConverter.getPhonePriceForModel(price));
            phone.setColor(rs.getString("phones.color"));
            phone.setDisplaySize(rs.getInt("phones.displaySize"));
            phone.setWidth(getNullIfNull(rs, "phones.width"));
            phone.setLength(getNullIfNull(rs, "phones.length"));
            phone.setCamera(getNullIfNull(rs, "phones.camera"));
            return phone;
        }

        private Integer getNullIfNull(ResultSet rs, String columnName) throws SQLException {
            return rs.getInt(columnName) == 0 ? null : rs.getInt(columnName);
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
            List<OrderItem> items = new ArrayList<>();
            Order result = null;
            while (rs.next()) {
                if(result == null) {
                    result = orderRowMapper.mapRow(rs, rs.getRow());
                }
                long currentOrderId = rs.getLong("orders.id");
                if (result.getKey() != currentOrderId){
                    break;
                }
                Long itemId = rs.getLong("order_items.id");
                OrderItem orderItem = new OrderItem();
                orderItem.setKey(itemId);
                Phone phone = phoneRowMapper.mapRow(rs, rs.getRow());
                orderItem.setPhone(phone);
                orderItem.setOrder(result);
                orderItem.setQuantity(rs.getInt("order_items.quantity"));
                items.add(orderItem);
            }
            if (result != null){
                result.setOrderItems(new HashSet<OrderItem>(items));
            }
            return result;
        }
    }

    public static class MultipleOrderResultSetExtractor implements ResultSetExtractor<List<Order>> {

        private ResultSetExtractor<Order> singleResultSetExtractor;

        public MultipleOrderResultSetExtractor(
                ResultSetExtractor<Order> singleResultSetExtractor) {
            this.singleResultSetExtractor = singleResultSetExtractor;
        }

        @Override
        public List<Order> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<Order> result = new ArrayList<>();
            while (!rs.isAfterLast()) {
                Order orderReturned = singleResultSetExtractor.extractData(rs);
                result.add(orderReturned);
                if (!rs.isAfterLast()){
                    rs.previous();
                }
            }
            return result;
        }
    }
}
