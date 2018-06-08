package com.epam.preprod.db.dao.tranaction;

import java.sql.Connection;
import java.sql.SQLException;

public interface TransactionOperation<T> {
	T produce(Connection connection) throws SQLException;
}
