package com.epam.preprod.db.mapper;

import com.epam.preprod.entity.Manufacturer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ManufacturerMapper implements Mapper<Manufacturer> {
	private static final Logger LOG = LoggerFactory.getLogger(ManufacturerMapper.class);

	@Override
	public Manufacturer extract(ResultSet resultSet) throws SQLException {
		LOG.debug("ManufacturerMapper starts.");

		Manufacturer manufacturer = new Manufacturer();
		manufacturer.setId(resultSet.getInt("manufacturer_id"));
		manufacturer.setName(resultSet.getString("manufacturer_name"));

		LOG.info("Mapped manufacturer: -->  {}", manufacturer);
		LOG.debug("ManufacturerMapper finished.");
		return manufacturer;
	}
}
