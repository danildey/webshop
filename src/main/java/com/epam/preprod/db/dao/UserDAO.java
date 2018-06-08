package com.epam.preprod.db.dao;

import com.epam.preprod.entity.User;

import java.sql.Connection;
import java.sql.SQLException;

public interface UserDAO {
	User getUserByEmail(Connection connection, String email) throws SQLException;

	void create(Connection connection, User user) throws SQLException;

	boolean isUserExist(Connection connection, String email) throws SQLException;

}
