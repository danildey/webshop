package com.epam.preprod.db;

import com.epam.preprod.db.mapper.Mapper;
import com.epam.preprod.db.specification.MySqlSpecification;
import com.epam.preprod.db.specification.Specification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class JdbcTemplate<E> {
	private static final Logger LOG = LoggerFactory.getLogger(JdbcTemplate.class);

	private Mapper<E> mapper;

	public JdbcTemplate(Mapper<E> mapper) {
		this.mapper = mapper;
	}

	public List<E> getAllBySpecification(Connection connection, Specification specification) throws SQLException {
		LOG.debug("Start specification query execution.");
		LOG.debug("Input argument connection : connection --> {}", connection);

		MySqlSpecification mySqlSpecification = (MySqlSpecification) specification;
		List<E> entities = new ArrayList<>();
		String query = mySqlSpecification.toMySqlQuery();
		LOG.info("Generated specification query : query -->  {}", query);

		if (Objects.nonNull(mySqlSpecification.getParams())) {
			LOG.info("Execution query with parameters. ");
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
				mapParameters(pstmt, mySqlSpecification.getParams());
				LOG.info("Mapped PreparedStatement : pstmt --> {}", pstmt);
				ResultSet resultSet = pstmt.executeQuery();
				while (resultSet.next()) {
					E entity = mapper.extract(resultSet);
					entities.add(entity);
				}
			}
		} else {
			LOG.info("Execution query without parameters. ");
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
				ResultSet resultSet = pstmt.executeQuery();
				while (resultSet.next()) {
					E entity = mapper.extract(resultSet);
					entities.add(entity);
				}
			}
		}
		LOG.info("Execution query finished : entities --> {}", entities);
		return entities;
	}

	public int getCountBySpecification(Connection connection, Specification specification) throws SQLException {
		LOG.debug("Start specification query execution.");
		LOG.debug("Input argument connection : connection --> {}", connection);

		MySqlSpecification mySqlSpecification = (MySqlSpecification) specification;
		int count = 0;

		String countQuery = mySqlSpecification.countMySqlQuery();
		LOG.info("Generated specification query : query -->  {}", countQuery);

		if (Objects.nonNull(mySqlSpecification.getParams())) {
			LOG.info("Execution query with parameters. ");
			try (PreparedStatement pstmt = connection.prepareStatement(countQuery)) {
				mapParameters(pstmt, mySqlSpecification.getParams());
				LOG.info("Mapped PreparedStatement : pstmt  --> {}", pstmt);
				ResultSet resultSet = pstmt.executeQuery();
				if (resultSet.next()) {
					count = resultSet.getInt("count");
				}
			}
		} else {
			LOG.info("Execution query without parameters. ");
			try (PreparedStatement pstmt = connection.prepareStatement(countQuery)) {
				ResultSet resultSet = pstmt.executeQuery();
				if (resultSet.next()) {
					count = resultSet.getInt("count");
				}
			}
		}
		LOG.info("Execution query finished : count -->  {}", count);
		return count;
	}

	private void mapParameters(PreparedStatement ps, List<Object> params) throws SQLException {
		LOG.info("Start mapping parameters to PreparedStatement : params -->  {}", params);

		int i = 1;
		for (Object param : params) {
			if (param instanceof Date) {
				ps.setTimestamp(i++, new Timestamp(((Date) param).getTime()));
				LOG.info("Mapped Date parameter : param -->  {}", param);
			} else if (param instanceof Integer) {
				ps.setInt(i++, (Integer) param);
				LOG.info("Mapped Integer parameter : param -->  {}", param);
			} else if (param instanceof Long) {
				ps.setLong(i++, (Long) param);
				LOG.info("Mapped Long parameter : param -->  {}", param);
			} else if (param instanceof Double) {
				ps.setDouble(i++, (Double) param);
				LOG.info("Mapped Double parameter : param -->  {}", param);
			} else if (param instanceof Float) {
				ps.setFloat(i++, (Float) param);
				LOG.info("Mapped Float parameter : param -->  {}", param);
			} else if (param instanceof BigDecimal) {
				ps.setBigDecimal(i++, (BigDecimal) param);
				LOG.info("Mapped BigDecimal parameter : param -->  {}", param);
			} else {
				ps.setString(i++, (String) param);
				LOG.info("Mapped String parameter : param -->  {}", param);
			}
		}
	}
}
