package com.epam.preprod.db.dao.tranaction;

import com.epam.preprod.exception.DBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
	private static final Logger LOG = LoggerFactory.getLogger(TransactionManager.class);

	private DataSource dataSource;

	public TransactionManager(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public <T> T execute(TransactionOperation<T> operation) throws DBException {
		T result;
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);

			result = operation.produce(connection);

			connection.commit();
			connection.close();
		} catch (SQLException e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException e1) {
				LOG.warn("Cannot rollback operation. ", e1);
				throw new DBException(e1.getMessage());
			}
			throw new DBException(e.getMessage());
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					LOG.warn("Cannot close a connection.", e);
				}
			}
		}
		return result;
	}
}
