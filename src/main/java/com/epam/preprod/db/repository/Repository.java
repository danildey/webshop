package com.epam.preprod.db.repository;

import com.epam.preprod.db.specification.Specification;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Repository<T> {
	void save(Connection connection, T obj) throws SQLException;

	void delete(Connection connection, T obj) throws SQLException;

	void update(Connection connection, T obj) throws SQLException;

	List<T> query(Connection connection, Specification specification) throws SQLException;

	int getCount(Connection connection, Specification specification) throws SQLException;
}
