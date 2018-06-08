package com.epam.preprod.db.specification.mysql;

import com.epam.preprod.db.querybuilder.SelectBuilder;
import com.epam.preprod.db.specification.MySqlSpecification;
import com.epam.preprod.web.bean.ProductFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.epam.preprod.db.enumeration.MysqlProductFields.*;

public class GetFilteredProductMySqlSpecification implements MySqlSpecification {
	private List<Object> parameters;
	private ProductFilter filterBean;
	private SelectBuilder queryBuilder;
	private SelectBuilder subQueryBuilder;

	public GetFilteredProductMySqlSpecification(ProductFilter filterBean) {

		this.filterBean = filterBean;
	}

	@Override
	public String toMySqlQuery() {
		parameters = new ArrayList<>();

		queryBuilder = new SelectBuilder()
				.column(ID.fieldName())
				.column(NAME.fieldName())
				.column(DESCRIPTION.fieldName())
				.column(PRICE.fieldName())
				.column(QUANTITY.fieldName())
				.column(CATEGORY_ID.fieldName())
				.column(MANUFACTURER_ID.fieldName())
				.column(IMAGE.fieldName())
				.column("category.category_name")
				.column("manufacturer.manufacturer_name")
				.from("product")
				.join("category ON " + CATEGORY_ID.fieldName() + " = category.category_id")
				.join("manufacturer ON " + MANUFACTURER_ID.fieldName() + "= manufacturer.manufacturer_id");

		if (Objects.nonNull(filterBean.getPage()) && Objects.nonNull(filterBean.getProductLimit())) {
			subQueryBuilder = new SelectBuilder().column("product.product_id")
					.from("product")
					.limit(filterBean.getProductLimit())
					.offset(filterBean.getPage() * filterBean.getProductLimit());
			String subQuery = subQueryBuilder.toString();
			queryBuilder.join("(" + subQuery + ") as p ON p.product_id = " + ID.fieldName());
			parameters.addAll(subQueryBuilder.getParameters());
		}
		if (Objects.nonNull(filterBean.getCategories())) {
			queryBuilder.whereIn(" category.category_id ", filterBean.getCategories());
		}
		if (Objects.nonNull(filterBean.getManufacturers())) {
			queryBuilder.whereIn(" manufacturer.manufacturer_id ", filterBean.getManufacturers());
		}
		if (Objects.nonNull(filterBean.getMaxPrice()) && Objects.nonNull(filterBean.getMinPrice())) {
			queryBuilder.whereBetween(PRICE.fieldName(), filterBean.getMinPrice(), filterBean.getMaxPrice());
		}
		if (Objects.nonNull(filterBean.getName())) {
			queryBuilder.whereLike(NAME.fieldName(), filterBean.getName() + "%");
		}
		if (Objects.nonNull(filterBean.getOrderBy())) {
			queryBuilder.orderBy(filterBean.getOrderBy().fieldName(), filterBean.isDescendingly());
		}

		parameters.addAll(queryBuilder.getParameters());
		return queryBuilder.toString();
	}

	@Override
	public String countMySqlQuery() {
		parameters = new ArrayList<>();
		this.queryBuilder = new SelectBuilder()
				.count()
				.from("product")
				.join("category ON " + CATEGORY_ID.fieldName() + " = category.category_id")
				.join("manufacturer ON " + MANUFACTURER_ID.fieldName() + "= manufacturer.manufacturer_id");

		if (Objects.nonNull(filterBean.getCategories())) {
			queryBuilder.whereIn(" category.category_id ", filterBean.getCategories());
		}
		if (Objects.nonNull(filterBean.getManufacturers())) {
			queryBuilder.whereIn(" manufacturer.manufacturer_id ", filterBean.getManufacturers());
		}
		if (Objects.nonNull(filterBean.getMaxPrice()) && Objects.nonNull(filterBean.getMinPrice())) {
			queryBuilder.whereBetween(PRICE.fieldName(), filterBean.getMinPrice(), filterBean.getMaxPrice());
		}
		if (Objects.nonNull(filterBean.getName())) {
			queryBuilder.whereLike(NAME.fieldName(), filterBean.getName() + "%");
		}

		parameters.addAll(queryBuilder.getParameters());
		return queryBuilder.toString();
	}

	@Override
	public List<Object> getParams() {
		return parameters;
	}

	public static void main(String[] args) {
		ProductFilter filter = new ProductFilter();
		GetFilteredProductMySqlSpecification sp = new GetFilteredProductMySqlSpecification(filter);
		System.out.println(sp.toMySqlQuery());
	}
}
