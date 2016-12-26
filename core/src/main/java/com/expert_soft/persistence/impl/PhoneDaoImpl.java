package com.expert_soft.persistence.impl;

import com.expert_soft.model.Phone;
import com.expert_soft.persistence.PhoneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("PhoneDao")
public class PhoneDaoImpl implements PhoneDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Phone getPhone(Long key) {
        return this.jdbcTemplate.queryForObject(
                "select id, model, price from phones where id = ?",
                new Object[]{key},
                new RowMapper<Phone>() {
                    public Phone mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Phone phone1 = new Phone();
                        phone1.setKey(rs.getLong(1));
                        phone1.setModel(rs.getString("model"));
                        phone1.setPrice(rs.getBigDecimal("price"));
                        return phone1;
                    }
                });
    }

    @Override
    public void savePhone(Phone phone) {
//        this.jdbcTemplate.update(
//                "insert into phones (id, model, price) values (?, ?)",
//                "Leonor", "Watling");
    }

    @Override
    public List<Phone> findAll() {
        return null;
    }
}
