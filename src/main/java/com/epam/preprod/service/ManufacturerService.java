package com.epam.preprod.service;

import com.epam.preprod.entity.Manufacturer;
import com.epam.preprod.exception.DBException;

import java.util.List;

public interface ManufacturerService {

	List<Manufacturer> getAllManufacturers() throws DBException;
}
