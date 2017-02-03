package com.expert_soft.persistence.impl;


import com.expert_soft.model.OrderItem;
import com.expert_soft.model.order.Order;
import com.expert_soft.persistence.OrderDao;
import com.expert_soft.persistence.impl.util.DataConverter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.util.List;

@Repository("orderDao")
@Transactional(isolation = Isolation.SERIALIZABLE)
public class OrderDaoImpl implements OrderDao {

    private static final String INSERT_ONE_ORDER_QUERY =
            "INSERT INTO orders (delivery_price, subtotal, total, " +
                    "first_name, last_name, delivery_address,\n" +
                    "contact_phone_no, additional_info)" +
                    "VALUES (:delivery_price, :subtotal, :total," +
                    " :first_name, :last_name, :delivery_address,\n" +
                    " :contact_phone_no, :additional_info)";

    private static final String INSERT_ONE_ITEM_QUERY =
            "INSERT INTO order_items(phone_id, order_id, quantity, subtotal) " +
            "VALUES (:phone_id, :order_id, :quantity, :subtotal)";

    private static final String GET_ALL_QUERY =
            " SELECT orders.id, orders.delivery_price, orders.subtotal, orders.total " +
                    ",orders.first_name, orders.last_name\n" +
                    ",orders.delivery_address, orders.contact_phone_no, orders.additional_info  " +
                    ",order_items.id, order_items.quantity, order_items.subtotal \n" +
                    ",phones.id, phones.model, phones.color, phones.displaySize, phones.width " +
                    ",phones.length " +
                    ",phones.camera, phones.price \n" +
                    "FROM orders \n" +
                    "left JOIN order_items \n" +
                    "ON order_items.order_id = orders.id \n" +
                    "JOIN phones \n" +
                    "ON phones.id = order_items.PHONE_ID \n";

    private static final String GET_ONE_QUERY = GET_ALL_QUERY + " WHERE ORDERS.id = ?";

    private NamedParameterJdbcTemplate jdbcTemplate;
    private ResultSetExtractor<Order> singleOrderExtractor;
    private ResultSetExtractor<List<Order>> listOrderExtractor;


    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setSingleOrderExtractor(ResultSetExtractor<Order> singleOrderExtractor) {
        this.singleOrderExtractor = singleOrderExtractor;
    }

    public void setListOrderExtractor(ResultSetExtractor<List<Order>> listOrderExtractor) {
        this.listOrderExtractor = listOrderExtractor;
    }

    @Override
    public Order getOrder(Long key) {
        return this.jdbcTemplate.getJdbcOperations()
                                .query(GET_ONE_QUERY, new Object[]{key}, singleOrderExtractor);
    }

    @Override
    public List<Order> findAll() {
        return this.jdbcTemplate.getJdbcOperations()
                                .query(con -> con.prepareStatement(GET_ALL_QUERY,
                                        ResultSet.TYPE_SCROLL_SENSITIVE,
                                        ResultSet.CONCUR_READ_ONLY), listOrderExtractor);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Long saveOrder(Order order) {
        KeyHolder holder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(INSERT_ONE_ORDER_QUERY, getParameterSource(order), holder);
        Number key = holder.getKey();
        saveOrderItems(order.getOrderItems(), key.longValue());
        return key.longValue();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    private void saveOrderItems(List<OrderItem> itemSet, Long orderKey){
        for (OrderItem item : itemSet){
            jdbcTemplate.update(INSERT_ONE_ITEM_QUERY, getParameterSource(item, orderKey));
        }
    }

    private MapSqlParameterSource getParameterSource(Order order){
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("delivery_price", DataConverter
                .getPriceForPersistence(order.getDeliveryPrice()));
        source.addValue("subtotal", DataConverter
                .getPriceForPersistence(order.getSubtotal()));
        source.addValue("total", DataConverter
                .getPriceForPersistence(order.getTotalPrice()));

        source.addValue("delivery_address", order.getDeliveryAddress());
        source.addValue("first_name", order.getFirstName());
        source.addValue("last_name", order.getLastName());
        source.addValue("contact_phone_no", order.getContactPhoneNo());
        source.addValue("additional_info", order.getAdditionalInfo());
        return source;
    }

    private MapSqlParameterSource getParameterSource(OrderItem item, Long orderKey){
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("phone_id", item.getPhone().getKey());
        source.addValue("order_id", orderKey);
        source.addValue("quantity", item.getQuantity());
        source.addValue("subtotal", item.getSubtotal());
        return source;
    }


}
