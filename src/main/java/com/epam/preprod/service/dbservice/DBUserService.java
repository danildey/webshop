package com.epam.preprod.service.dbservice;

import com.epam.preprod.db.dao.UserDAO;
import com.epam.preprod.db.dao.tranaction.TransactionManager;
import com.epam.preprod.entity.User;
import com.epam.preprod.exception.DBException;
import com.epam.preprod.service.UserService;

public class DBUserService implements UserService {
	private TransactionManager transactionManager;
	private UserDAO userDAO;

	public DBUserService(TransactionManager transactionManager, UserDAO userDAO) {
		this.transactionManager = transactionManager;
		this.userDAO = userDAO;
	}

	@Override
	public void createUser(User user) throws DBException {
		transactionManager.execute(connection -> {
					userDAO.create(connection, user);
					return null;
				}
		);
	}

	@Override
	public User getUser(String email) throws DBException {
		return transactionManager.execute(connection -> {
					return userDAO.getUserByEmail(connection, email);
				}
		);
	}

	@Override
	public boolean isUserExist(String email) throws DBException {
		return transactionManager.execute(connection -> {
					return userDAO.isUserExist(connection, email);
				}
		);
	}
}



