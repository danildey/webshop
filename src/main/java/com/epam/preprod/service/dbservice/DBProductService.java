package com.epam.preprod.service.dbservice;

import com.epam.preprod.db.dao.tranaction.TransactionManager;
import com.epam.preprod.db.repository.Repository;
import com.epam.preprod.db.specification.Specification;
import com.epam.preprod.db.specification.mysql.GetFilteredProductMySqlSpecification;
import com.epam.preprod.entity.Product;
import com.epam.preprod.service.ProductService;
import com.epam.preprod.web.bean.ProductFilter;

import java.util.List;

public class DBProductService implements ProductService {
	private TransactionManager transactionManager;
	private Repository<Product> repository;

	public DBProductService(TransactionManager transactionManager, Repository<Product> repository) {
		this.transactionManager = transactionManager;
		this.repository = repository;
	}

	@Override
	public List<Product> getFilteredProducts(ProductFilter filterBean) {
		Specification specification = new GetFilteredProductMySqlSpecification(filterBean);
		return transactionManager.execute(connection -> {
					return repository.query(connection, specification);
				}
		);
	}

	@Override
	public int getFilteredProductsCount(ProductFilter filterBean) {
		Specification specification = new GetFilteredProductMySqlSpecification(filterBean);
		return transactionManager.execute(connection -> {
					return repository.getCount(connection, specification);
				}
		);
	}
}
