package com.expert_soft.persistence.impl;

import com.expert_soft.model.Phone;
import com.expert_soft.persistence.PhoneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Repository("PhoneDao")
public class PhoneDaoImpl implements PhoneDao {

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
        return this.jdbcTemplate.queryForObject("select id, model, price from phones where id = ?",
                new Object[]{key},
                phoneRowMapper);
    }

    @Override
    public void savePhone(Phone phone) {
        this.jdbcTemplate.update(
                "insert into phones (model, price) values (?, ?)",
                phone.getModel(), phone.getPrice());
    }

    @Override
    public List<Phone> findAll() {
       return this.jdbcTemplate.query("select id, model, price from phones",
               phoneRowMapper);
    }

}
