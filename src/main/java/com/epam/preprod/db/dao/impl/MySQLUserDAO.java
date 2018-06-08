package com.epam.preprod.db.dao.impl;

import com.epam.preprod.db.dao.UserDAO;
import com.epam.preprod.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLUserDAO implements UserDAO {
	private static final Logger LOG = LoggerFactory.getLogger(MySQLUserDAO.class);

	private static final String CHECK_IF_USER_EXIST = "SELECT 1 from user where email = ?;";
	private static final String GET_USER_BY_EMAIL = "SELECT * from user WHERE email = ?";
	private static final String INSERT_NEW_USER = "INSERT INTO webshop.user (first_name, last_name, email, password, is_sign_up,user_avatar_extension) VALUES (?, ?, ?, ?, ?, ?)";


	@Override
	public User getUserByEmail(Connection connection, String email) throws SQLException {
		User result = null;
		try (PreparedStatement pstmt = connection.prepareStatement(GET_USER_BY_EMAIL)) {
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				result = extractUser(rs);
			}
		}
		LOG.info("Get user by email from DB : result --> {}", result);
		return result;
	}

	@Override
	public void create(Connection connection, User user) throws SQLException {
		try (PreparedStatement pstmt = connection.prepareStatement(INSERT_NEW_USER)) {
			int k = 1;
			pstmt.setString(k++, user.getFirstName());
			pstmt.setString(k++, user.getLastName());
			pstmt.setString(k++, user.getEmail());
			pstmt.setString(k++, user.getPassword());
			pstmt.setBoolean(k++, user.isSignUp());
			pstmt.setString(k++, user.getUserAvatarExtension());
			pstmt.executeUpdate();
			LOG.info("Saved user in DB : user --> {}", user);
		}
	}

	@Override
	public boolean isUserExist(Connection connection, String email) throws SQLException {
		try (PreparedStatement pstmt = connection.prepareStatement(CHECK_IF_USER_EXIST)) {
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				LOG.info("User with email exist : email --> {}", email);
				return true;
			} else {
				LOG.info("User with email not exist : email --> {}", email);
				return false;
			}
		}
	}

	private User extractUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setFirstName(rs.getString("first_name"));
		user.setLastName(rs.getString("last_name"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setUserAvatarExtension(rs.getString("user_avatar_extension"));
		LOG.info("Extracted user : user --> {}", user);

		return user;
	}
}
