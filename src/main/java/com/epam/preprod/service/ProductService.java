package com.epam.preprod.service;

import com.epam.preprod.entity.Product;
import com.epam.preprod.web.bean.ProductFilter;

import java.util.List;

public interface ProductService {
	List<Product> getFilteredProducts(ProductFilter filterBean);

	int getFilteredProductsCount(ProductFilter filterBean);
}
