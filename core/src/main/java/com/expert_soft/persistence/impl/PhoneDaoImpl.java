package com.expert_soft.persistence.impl;

import com.expert_soft.model.Phone;
import com.expert_soft.persistence.PhoneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("phoneDao")
public class PhoneDaoImpl implements PhoneDao {

    private static final String INSERT_ONE_QUERY =
            "INSERT INTO phones (model, price) VALUES (?, ?)";

    private static final String GET_ALL_QUERY =
            "SELECT phones.id, phones.model, phones.price FROM phones";

    private static final String GET_ONE_QUERY = GET_ALL_QUERY + " WHERE id = ?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Phone> phoneRowMapper;

    @Autowired
    public void setDataSource(DataSource dataSource) throws SQLException {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Qualifier("phone_mapper")
    @Autowired
    public void setPhoneRowMapper(RowMapper<Phone> phoneRowMapper) {
        this.phoneRowMapper = phoneRowMapper;
    }

    @Override
    public Phone getPhone(Long key) {
        return this.jdbcTemplate.queryForObject(GET_ONE_QUERY, new Object[]{key}, phoneRowMapper);
    }

    @Override
    public void savePhone(Phone phone) {
        this.jdbcTemplate.update(INSERT_ONE_QUERY,phone.getModel(), phone.getPrice());
    }

    @Override
    public List<Phone> findAll() {
       return this.jdbcTemplate.query(GET_ALL_QUERY, phoneRowMapper);
    }

}
