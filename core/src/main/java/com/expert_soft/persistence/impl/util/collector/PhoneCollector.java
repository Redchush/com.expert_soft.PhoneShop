package com.expert_soft.persistence.impl.util.collector;


import com.expert_soft.model.Phone;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PhoneCollector implements ResultSetCollector<Phone> {

    @Override
    public Phone collectEntityFromObject(ResultSet resultSet) throws SQLException {
        return null;
    }

}
