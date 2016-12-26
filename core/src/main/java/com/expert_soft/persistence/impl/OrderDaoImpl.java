package com.expert_soft.persistence.impl;



import com.expert_soft.model.Order;
import com.expert_soft.persistence.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("OrderDao")
public class OrderDaoImpl implements OrderDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Order getOrder(Long key) {
//        Order order = this.jdbcTemplate.queryForObject(
//                "select first_name, last_name from t_actor where id = ?",
//                new Object[]{1212L},
//                new RowMapper<Order>() {
//                    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
//                        Order order = new Order();
//                        order.setKey(rs.getLong(1));
//                        order.se
//                        order.setFirstName(rs.getString("first_name"));
//                        order.setLastName(rs.getString("last_name"));
//                        return order;
//                    }
//                });
        return null;
    }

    @Override
    public void saveOrder(Order order) {

    }

    @Override
    public List<Order> findAll() {
        return null;
    }
}
