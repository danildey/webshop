package com.epam.preprod.service.dbservice;

import com.epam.preprod.db.dao.tranaction.TransactionManager;
import com.epam.preprod.db.repository.Repository;
import com.epam.preprod.db.specification.Specification;
import com.epam.preprod.db.specification.mysql.GetAllManufacturersMySqlSpecification;
import com.epam.preprod.entity.Manufacturer;
import com.epam.preprod.exception.DBException;
import com.epam.preprod.service.ManufacturerService;

import java.util.List;

public class DBManufacturerService implements ManufacturerService {
	private TransactionManager transactionManager;
	private Repository<Manufacturer> repository;


	public DBManufacturerService(TransactionManager transactionManager, Repository<Manufacturer> repository) {
		this.transactionManager = transactionManager;
		this.repository = repository;
	}

	@Override
	public List<Manufacturer> getAllManufacturers() throws DBException {
		Specification specification = new GetAllManufacturersMySqlSpecification();
		return transactionManager.execute(connection -> {
					return repository.query(connection, specification);
				}
		);
	}
}
