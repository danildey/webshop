package com.epam.preprod.web.bean;

import com.epam.preprod.db.enumeration.MysqlProductFields;

import java.util.List;

public class ProductFilter {
	private static final Integer DEFAULT_PRODUCT_LIMIT = 9;
	private static final Integer DEFAULT_PAGE = 0;

	private List<Integer> categories;
	private List<Integer> manufacturers;
	private MysqlProductFields orderBy;
	private Boolean descendingly;
	private Integer minPrice;
	private Integer maxPrice;
	private Integer productLimit;
	private Integer page;
	private String name;

	public ProductFilter() {
		this.productLimit = DEFAULT_PRODUCT_LIMIT;
		this.page = DEFAULT_PAGE;
	}

	public List<Integer> getCategories() {
		return categories;
	}

	public void setCategories(List<Integer> categories) {
		this.categories = categories;
	}

	public List<Integer> getManufacturers() {
		return manufacturers;
	}

	public void setManufacturers(List<Integer> manufacturers) {
		this.manufacturers = manufacturers;
	}

	public MysqlProductFields getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(MysqlProductFields orderBy) {
		this.orderBy = orderBy;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Integer minPrice) {
		this.minPrice = minPrice;
	}

	public Integer getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Integer maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Integer getProductLimit() {
		return productLimit;
	}

	public void setProductLimit(Integer productLimit) {
		this.productLimit = productLimit;
	}

	public boolean isDescendingly() {
		return descendingly;
	}

	public void setDescendingly(boolean descendingly) {
		this.descendingly = descendingly;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ProductFilter filter = (ProductFilter) o;

		if (categories != null ? !categories.equals(filter.categories) : filter.categories != null) return false;
		if (manufacturers != null ? !manufacturers.equals(filter.manufacturers) : filter.manufacturers != null)
			return false;
		if (orderBy != filter.orderBy) return false;
		if (descendingly != null ? !descendingly.equals(filter.descendingly) : filter.descendingly != null)
			return false;
		if (minPrice != null ? !minPrice.equals(filter.minPrice) : filter.minPrice != null) return false;
		if (maxPrice != null ? !maxPrice.equals(filter.maxPrice) : filter.maxPrice != null) return false;
		if (productLimit != null ? !productLimit.equals(filter.productLimit) : filter.productLimit != null)
			return false;
		if (page != null ? !page.equals(filter.page) : filter.page != null) return false;
		return name != null ? name.equals(filter.name) : filter.name == null;
	}

	@Override
	public int hashCode() {
		int result = categories != null ? categories.hashCode() : 0;
		result = 31 * result + (manufacturers != null ? manufacturers.hashCode() : 0);
		result = 31 * result + (orderBy != null ? orderBy.hashCode() : 0);
		result = 31 * result + (descendingly != null ? descendingly.hashCode() : 0);
		result = 31 * result + (minPrice != null ? minPrice.hashCode() : 0);
		result = 31 * result + (maxPrice != null ? maxPrice.hashCode() : 0);
		result = 31 * result + (productLimit != null ? productLimit.hashCode() : 0);
		result = 31 * result + (page != null ? page.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "ProductFilter{" +
				"categories=" + categories +
				", manufacturers=" + manufacturers +
				", minPrice=" + minPrice +
				", maxPrice=" + maxPrice +
				", orderBy='" + orderBy + '\'' +
				", descendingly=" + descendingly +
				", productLimit=" + productLimit +
				", page=" + page +
				", name='" + name + '\'' +
				'}';
	}
}
