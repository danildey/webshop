package com.epam.preprod.web.exctractor;

import com.epam.preprod.db.enumeration.MysqlProductFields;
import com.epam.preprod.web.bean.ProductFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductFilterBeanExtractor implements RequestExtractor<ProductFilter> {
	private static final Logger LOG = LoggerFactory.getLogger(ProductFilterBeanExtractor.class);

	private static final String PRODUCT_FILTER_BEAN = "productFilter";
	private static final String CATEGORIES = "category-checkbox";
	private static final String MANUFACTURERS = "manufacturer-checkbox";
	private static final String ORDER_BY = "orderBy";
	private static final String DESC = "desc";
	private static final String NAME = "name";
	private static final String MIN_PRICE = "minPrice";
	private static final String MAX_PRICE = "maxPrice";
	private static final String LIMIT = "limit";
	private static final String PAGE = "page";

	public ProductFilterBeanExtractor() {
	}

	@Override
	public ProductFilter extractFromRequest(HttpServletRequest req) throws IOException, ServletException {
		LOG.debug("ProductFilterBeanExtractor starts");
		ProductFilter filterBean = (ProductFilter) req.getSession().getAttribute(PRODUCT_FILTER_BEAN);
		if (Objects.isNull(filterBean)) {
			filterBean = new ProductFilter();
		} else {
			filterBean = extractFilter(req, filterBean);
		}
		return filterBean;
	}

	private ProductFilter extractFilter(HttpServletRequest req, ProductFilter filterBean) {
		LOG.info("Get  all parameters from request : --> {}", req.getParameterMap());

		String[] categories = req.getParameterValues(CATEGORIES);
		LOG.info("Get  categories from request : --> {}", categories);

		String[] manufacturers = req.getParameterValues(MANUFACTURERS);
		LOG.info("Get  manufacturers from request : --> {}", manufacturers);

		String orderBy = req.getParameter(ORDER_BY);
		LOG.info("Get  orderBy from request : --> {}", orderBy);

		String name = req.getParameter(NAME);
		LOG.info("Get  name from request : --> {}", name);

		String descendingly = req.getParameter(DESC);
		LOG.info("Get  descendingly from request : --> {}", descendingly);

		int minPrice = checkInteger(req.getParameter(MIN_PRICE));
		LOG.info("Get  minPrice from request : --> {}", minPrice);

		int maxPrice = checkInteger(req.getParameter(MAX_PRICE));
		LOG.info("Get  maxPrice from request : --> {}", maxPrice);

		int limit = checkInteger(req.getParameter(LIMIT));
		LOG.info("Get  limit from request : --> {}", limit);

		int page = checkInteger(req.getParameter(PAGE));
		LOG.info("Get  page from request : --> {}", page);


		List<Integer> categoryList = parseArray(categories);
		filterBean.setCategories(categoryList);

		List<Integer> manufacturerList = parseArray(manufacturers);
		filterBean.setManufacturers(manufacturerList);

		if (Objects.nonNull(orderBy) && orderBy.length() != 0) {
			try {
				MysqlProductFields field = MysqlProductFields.valueOf(orderBy.toUpperCase());
				filterBean.setOrderBy(field);
			} catch (IllegalArgumentException ex) {
				LOG.info("Wrong orderBy parameter : --> {}", orderBy);
			}
		}
		if (Objects.nonNull(descendingly) && descendingly.length() != 0) {
			filterBean.setDescendingly(Boolean.parseBoolean(descendingly));
		}
		if (Objects.nonNull(name) && name.length() != 0) {
			filterBean.setName(name);
		}
		if (minPrice != -1) {
			filterBean.setMinPrice(minPrice);
		}
		if (maxPrice != -1) {
			filterBean.setMaxPrice(maxPrice);
		}
		if (limit != -1) {
			filterBean.setProductLimit(limit);
		}
		if (page != -1) {
			filterBean.setPage(page);
		}

		LOG.info("Extracted filterBean: -->  {}", filterBean);
		LOG.debug("ProductFilterBeanExtractor finished.");

		return filterBean;
	}

	private List<Integer> parseArray(String[] categories) {
		List<Integer> categoryList = null;
		if (Objects.nonNull(categories)) {
			categoryList = new ArrayList<>();
			for (String category : categories) {
				categoryList.add(Integer.parseInt(category));
			}
		}
		return categoryList;
	}

	private int checkInteger(String value) {
		try {
			int parsedValue = Integer.parseInt(value);
			if (parsedValue >= 0) {
				return parsedValue;
			}
		} catch (NumberFormatException e) {
			LOG.debug("Wrong number format of input parameter.");
		}
		return -1;
	}
}
