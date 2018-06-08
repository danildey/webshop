package com.epam.preprod.service.dbservice;

import com.epam.preprod.db.dao.tranaction.TransactionManager;
import com.epam.preprod.db.repository.Repository;
import com.epam.preprod.db.specification.Specification;
import com.epam.preprod.db.specification.mysql.GetAllCategoriesMySqlSpecification;
import com.epam.preprod.entity.Category;
import com.epam.preprod.exception.DBException;
import com.epam.preprod.service.CategoryService;

import java.util.List;

public class DBCategoryService implements CategoryService {
	private TransactionManager transactionManager;
	private Repository<Category> repository;

	public DBCategoryService(TransactionManager transactionManager, Repository<Category> repository) {
		this.transactionManager = transactionManager;
		this.repository = repository;
	}

	@Override
	public List<Category> getAllCategories() throws DBException {
		Specification specification = new GetAllCategoriesMySqlSpecification();
		return transactionManager.execute(connection -> {
					return repository.query(connection, specification);
				}
		);
	}
}