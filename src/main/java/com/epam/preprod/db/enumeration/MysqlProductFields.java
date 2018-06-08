package com.epam.preprod.db.enumeration;

public enum MysqlProductFields {
	ID("product.product_id"),
	NAME("product.product_name"),
	PRICE("product.product_price"),
	DESCRIPTION("product.product_description"),
	QUANTITY("product.product_quantity"),
	CATEGORY_ID("product.category_id"),
	MANUFACTURER_ID("product.manufacturer_id"),
	IMAGE("product.product_image");

	private String fieldName;

	MysqlProductFields(String fieldName) {
		this.fieldName = fieldName;
	}

	public String fieldName() {
		return fieldName;
	}
}
