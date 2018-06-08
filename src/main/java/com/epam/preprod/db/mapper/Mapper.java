package com.epam.preprod.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Mapper<E> {
	E extract(ResultSet resultSet) throws SQLException;
}