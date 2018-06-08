package com.epam.preprod.db.specification;

import java.util.List;

public interface MySqlSpecification extends Specification {
	String toMySqlQuery();

	String countMySqlQuery();

	List<Object> getParams();
}
