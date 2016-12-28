package com.expert_soft.persistence.impl.util.collector;


import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetCollector<T> {

    T collectEntityFromObject(ResultSet resultSet) throws SQLException;

}
