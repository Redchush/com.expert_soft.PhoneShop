package com.expert_soft.persistence.impl;

import com.expert_soft.model.Phone;
import com.expert_soft.persistence.PhoneDao;
import com.expert_soft.persistence.impl.util.DataConverter;
import org.apache.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Repository("phoneDao")
@Transactional(isolation = Isolation.SERIALIZABLE)
public class PhoneDaoImpl implements PhoneDao {

    private static final Logger LOGGER = Logger.getLogger(PhoneDaoImpl.class);

    private static final String INSERT_ONE_QUERY =
            "INSERT INTO phones (model, color, displaySize, width, length, camera, price) " +
                    "VALUES (:model, :color, :displaySize, :width, :length, :camera, :price)";

    private static final String BASE_QUERY =
            "SELECT phones.id, phones.model, phones.color, phones.displaySize, phones.width, phones.length," +
                   "phones.camera, phones.price " +
                   "FROM phones";

    private static final String GET_ALL_QUERY = BASE_QUERY + "\n ORDER BY PHONES.ID";
    private static final String GET_ONE_QUERY = BASE_QUERY + "\n  WHERE id = ?";
    private static final String GET_GROUP_QUERY = BASE_QUERY + "\n  WHERE id in (:ids)";

    private NamedParameterJdbcTemplate jdbcTemplate;
    private RowMapper<Phone> phoneRowMapper;

    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setPhoneRowMapper(RowMapper<Phone> phoneRowMapper) {
        this.phoneRowMapper = phoneRowMapper;
    }

    @Override
    public Phone getPhone(Long key) {
        if (key < 1){
            return null;
        }
        try {
           return this.jdbcTemplate.getJdbcOperations()
                             .queryForObject(GET_ONE_QUERY, new Object[]{key}, phoneRowMapper);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public List<Phone> getPhones(Long... keys){
        if (keys == null || keys.length == 0){
            return Collections.emptyList();
        }
        try {
           return this.jdbcTemplate.query(GET_GROUP_QUERY,
                                    getParameterSource(keys), phoneRowMapper);
        } catch (EmptyResultDataAccessException e){
            return Collections.emptyList();
        }
    }

    @Override
    public List<Phone> getPhones(Collection<Long> keys) {
        if (keys == null || keys.size() == 0){
            return Collections.emptyList();
        }
        try{
           return this.jdbcTemplate.query(GET_GROUP_QUERY,
                    getParameterSource(keys), phoneRowMapper);
        } catch (EmptyResultDataAccessException e){
            return Collections.emptyList();
        }
    }


    @Override
    public Number savePhone(Phone phone) {
        try{
            KeyHolder holder = new GeneratedKeyHolder();
            this.jdbcTemplate.update(INSERT_ONE_QUERY, getParameterSource(phone), holder);
            return holder.getKey();
        } catch (DuplicateKeyException e){
            return null;
        }
    }

    @Override
    public List<Phone> findAll() {
        return this.jdbcTemplate.query(GET_ALL_QUERY, phoneRowMapper);
    }

    private MapSqlParameterSource getParameterSource(Phone phone){
        MapSqlParameterSource result = new MapSqlParameterSource();
        result.addValue("model", phone.getModel());
        result.addValue("color", phone.getColor());
        result.addValue("displaySize", phone.getDisplaySize());
        result.addValue("width", phone.getWidth());
        result.addValue("length", phone.getLength());
        result.addValue("camera", phone.getCamera());
        result.addValue("price", DataConverter.getPriceForPersistence(phone.getPrice()));
        return result;
    }

    private MapSqlParameterSource getParameterSource(Long[] ids){
        MapSqlParameterSource result = new MapSqlParameterSource();
        result.addValue("ids", Arrays.asList(ids));
        return result;
    }

    private MapSqlParameterSource  getParameterSource(Collection<Long> keys) {
        MapSqlParameterSource result = new MapSqlParameterSource();
        result.addValue("ids", keys);
        return result;
    }

}
