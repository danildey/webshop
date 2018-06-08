package com.epam.preprod.db.repository.mysql;

import com.epam.preprod.db.JdbcTemplate;
import com.epam.preprod.db.mapper.ManufacturerMapper;
import com.epam.preprod.db.mapper.Mapper;
import com.epam.preprod.db.repository.Repository;
import com.epam.preprod.db.specification.Specification;
import com.epam.preprod.entity.Manufacturer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class MySqlManufacturerRepository implements Repository<Manufacturer> {
	private static final Logger LOG = LoggerFactory.getLogger(MySqlManufacturerRepository.class);

	private static final String INSERT_NEW_MANUFACTURER = "INSERT INTO `webshop`.`manufacturer` (`name`) VALUES (?)";
	private static final String DELETE_MANUFACTURER_BY_ID = "DELETE FROM `webshop`.`manufacturer` WHERE `id`= ?";
	private static final String UPDATE_MANUFACTURER_BY_ID = "UPDATE `webshop`.`manufacturer` SET `name`= ? WHERE `id`= ?";

	private Mapper<Manufacturer> mapper;
	private JdbcTemplate<Manufacturer> jdbcTemplate;

	public MySqlManufacturerRepository() {
		this.mapper = new ManufacturerMapper();
		jdbcTemplate = new JdbcTemplate<>(mapper);
	}

	@Override
	public void save(Connection connection, Manufacturer manufacturer) throws SQLException {
		try (PreparedStatement pstmt = connection.prepareStatement(INSERT_NEW_MANUFACTURER)) {
			int k = 1;
			pstmt.setString(k++, manufacturer.getName());
			pstmt.executeUpdate();
			LOG.info("Saved manufacturer in DB : manufacturer --> {}", manufacturer);
		}
	}

	@Override
	public void delete(Connection connection, Manufacturer manufacturer) throws SQLException {
		try (PreparedStatement pstmt = connection.prepareStatement(DELETE_MANUFACTURER_BY_ID)) {
			int k = 1;
			pstmt.setInt(k++, manufacturer.getId());
			pstmt.executeUpdate();
			LOG.info("Deleted manufacturer in DB : manufacturer --> {}", manufacturer);
		}
	}

	@Override
	public void update(Connection connection, Manufacturer manufacturer) throws SQLException {
		try (PreparedStatement pstmt = connection.prepareStatement(UPDATE_MANUFACTURER_BY_ID)) {
			int k = 1;
			pstmt.setString(k++, manufacturer.getName());
			pstmt.setInt(k++, manufacturer.getId());
			pstmt.executeUpdate();
			LOG.info("Updated manufacturer in DB : manufacturer --> {}", manufacturer);
		}
	}

	@Override
	public List<Manufacturer> query(Connection connection, Specification specification) throws SQLException {
		return jdbcTemplate.getAllBySpecification(connection, specification);
	}

	@Override
	public int getCount(Connection connection, Specification specification) throws SQLException {
		return jdbcTemplate.getCountBySpecification(connection, specification);
	}
}
