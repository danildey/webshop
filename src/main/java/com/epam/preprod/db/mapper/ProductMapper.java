package com.epam.preprod.db.mapper;

import com.epam.preprod.entity.Category;
import com.epam.preprod.entity.Manufacturer;
import com.epam.preprod.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements Mapper<Product> {
	private static final Logger LOG = LoggerFactory.getLogger(ProductMapper.class);

	private Mapper<Category> categoryMapper;
	private Mapper<Manufacturer> manufacturerMapper;

	public ProductMapper() {
		categoryMapper = new CategoryMapper();
		manufacturerMapper = new ManufacturerMapper();
	}

	@Override
	public Product extract(ResultSet resultSet) throws SQLException {
		LOG.debug("ProductMapper starts.");

		Product product = new Product();
		product.setId(resultSet.getInt("product_id"));
		product.setName(resultSet.getString("product_name"));
		product.setDescription(resultSet.getString("product_description"));
		product.setPrice(resultSet.getBigDecimal("product_price"));
		product.setQuantity(resultSet.getInt("product_quantity"));
		product.setImage(resultSet.getString("product_image"));

		Category category = categoryMapper.extract(resultSet);
		product.setCategory(category);

		Manufacturer manufacturer = manufacturerMapper.extract(resultSet);
		product.setManufacturer(manufacturer);

		LOG.info("Mapped product: -->  {}", product);
		LOG.debug("ProductMapper finished.");
		return product;
	}
}
