package com.epam.preprod.service;

import com.epam.preprod.entity.User;
import com.epam.preprod.exception.DBException;

public interface UserService {
	void createUser(User user) throws DBException;

	User getUser(String email) throws DBException;

	boolean isUserExist(String email) throws DBException;
}
