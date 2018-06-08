package com.epam.preprod.service;

import com.epam.preprod.entity.Category;
import com.epam.preprod.exception.DBException;

import java.util.List;

public interface CategoryService {
	List<Category> getAllCategories() throws DBException;
}
