package com.epam.preprod.db.mapper;

import com.epam.preprod.entity.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper implements Mapper<Category> {
	private static final Logger LOG = LoggerFactory.getLogger(CategoryMapper.class);

	@Override
	public Category extract(ResultSet resultSet) throws SQLException {
		LOG.debug("CategoryMapper starts.");

		Category category = new Category();
		category.setId(resultSet.getInt("category_id"));
		category.setName(resultSet.getString("category_name"));

		LOG.info("Mapped category: -->  {}", category);
		LOG.debug("CategoryMapper finished.");
		return category;
	}
}
