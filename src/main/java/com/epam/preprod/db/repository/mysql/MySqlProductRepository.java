package com.epam.preprod.db.repository.mysql;

import com.epam.preprod.db.JdbcTemplate;
import com.epam.preprod.db.mapper.Mapper;
import com.epam.preprod.db.mapper.ProductMapper;
import com.epam.preprod.db.repository.Repository;
import com.epam.preprod.db.specification.Specification;
import com.epam.preprod.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class MySqlProductRepository implements Repository<Product> {
	private static final Logger LOG = LoggerFactory.getLogger(MySqlProductRepository.class);

	private static final String INSERT_NEW_PRODUCT = "INSERT INTO `webshop`.`product` (`name`, `description`, `price`, `quantity`, `manufacturer_id`, `category_id`) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String DELETE_PRODUCT = "DELETE FROM `webshop`.`product` WHERE `id`=?";
	private static final String UPDATE_PRODUCT = "UPDATE `webshop`.`product` SET `name`=?, `description`=?, `price`=?, `quantity`=?, `manufacturer_id`=?, `category_id`=? WHERE `id`=?";

	private Mapper<Product> mapper;
	private JdbcTemplate<Product> jdbcTemplate;

	public MySqlProductRepository() {
		this.mapper = new ProductMapper();
		jdbcTemplate = new JdbcTemplate<>(mapper);
	}


	@Override
	public void save(Connection connection, Product product) throws SQLException {
		try (PreparedStatement pstmt = connection.prepareStatement(INSERT_NEW_PRODUCT)) {
			int k = 1;
			pstmt.setString(k++, product.getName());
			pstmt.setString(k++, product.getDescription());
			pstmt.setBigDecimal(k++, product.getPrice());
			pstmt.setInt(k++, product.getQuantity());
			pstmt.setInt(k++, product.getManufacturer().getId());
			pstmt.setInt(k++, product.getCategory().getId());
			pstmt.executeUpdate();
			LOG.info("Saved product in DB : product --> {}", product);
		}
	}

	@Override
	public void delete(Connection connection, Product product) throws SQLException {
		try (PreparedStatement pstmt = connection.prepareStatement(DELETE_PRODUCT)) {
			int k = 1;
			pstmt.setInt(k++, product.getId());
			pstmt.executeUpdate();
			LOG.info("Deleted product in DB : product --> {}", product);
		}
	}

	@Override
	public void update(Connection connection, Product product) throws SQLException {
		try (PreparedStatement pstmt = connection.prepareStatement(UPDATE_PRODUCT)) {
			int k = 1;
			pstmt.setString(k++, product.getName());
			pstmt.setString(k++, product.getDescription());
			pstmt.setBigDecimal(k++, product.getPrice());
			pstmt.setInt(k++, product.getQuantity());
			pstmt.setInt(k++, product.getManufacturer().getId());
			pstmt.setInt(k++, product.getCategory().getId());
			pstmt.setInt(k++, product.getId());
			pstmt.executeUpdate();
			LOG.info("Updated product in DB : product --> {}", product);
		}
	}

	@Override
	public List<Product> query(Connection connection, Specification specification) throws SQLException {
		return jdbcTemplate.getAllBySpecification(connection, specification);
	}

	@Override
	public int getCount(Connection connection, Specification specification) throws SQLException {
		return jdbcTemplate.getCountBySpecification(connection, specification);
	}
}
