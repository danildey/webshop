package com.epam.preprod.db.specification.mysql;

import com.epam.preprod.db.specification.MySqlSpecification;

import java.util.List;

public class GetAllCategoriesMySqlSpecification implements MySqlSpecification {
	@Override
	public String toMySqlQuery() {
		return "SELECT * FROM webshop.category";
	}

	@Override
	public String countMySqlQuery() {
		return "SELECT  COUNT(*) AS count FROM webshop.category";
	}

	@Override
	public List<Object> getParams() {
		return null;
	}
}
