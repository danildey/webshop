package com.epam.preprod.web.servlet;

import com.epam.preprod.entity.Category;
import com.epam.preprod.entity.Manufacturer;
import com.epam.preprod.entity.Product;
import com.epam.preprod.service.CategoryService;
import com.epam.preprod.service.ManufacturerService;
import com.epam.preprod.service.ProductService;
import com.epam.preprod.web.bean.ProductFilter;
import com.epam.preprod.web.exctractor.ProductFilterBeanExtractor;
import com.epam.preprod.web.exctractor.RequestExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/products")
public class ProductFilterServlet extends HttpServlet {
	private static final Logger LOG = LoggerFactory.getLogger(ProductFilterServlet.class);

	private final String ALL_CATEGORIES_ATTRIBUTE = "allCategories";
	private final String FILTERED_PRODUCTS_ATTRIBUTE = "filteredProducts";
	private final String ALL_MANUFACTURERS_ATTRIBUTE = "allManufacturers";
	private final String COUNT_PRODUCTS_ATTRIBUTE = "countProducts";
	private final String PRODUCT_FILTER_ATTRIBUTE = "productFilter";

	private static final String ALL_PRODUCTS_PAGE = "/allProducts";

	private RequestExtractor<ProductFilter> productFilterBeanExtractor;
	private static final String PRODUCT_FILTER_BEAN_EXTRACTOR = "filterBeanExtractor";
	private static final String PRODUCT_SERVICE = "productService";
	private static final String CATEGORY_SERVICE = "categoryService";
	private static final String MANUFACTURER_SERVICE = "manufacturerService";

	private ProductService productService;
	private CategoryService categoryService;
	private ManufacturerService manufacturerService;

	@Override
	public void init() throws ServletException {
		productFilterBeanExtractor = (ProductFilterBeanExtractor) getServletContext().getAttribute(PRODUCT_FILTER_BEAN_EXTRACTOR);

		productService = (ProductService) getServletContext().getAttribute(PRODUCT_SERVICE);
		categoryService = (CategoryService) getServletContext().getAttribute(CATEGORY_SERVICE);
		manufacturerService = (ManufacturerService) getServletContext().getAttribute(MANUFACTURER_SERVICE);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOG.debug("doGet() called");

		ProductFilter productFilter = productFilterBeanExtractor.extractFromRequest(req);

		List<Category> allCategories = categoryService.getAllCategories();
		LOG.info("Get List of all categories from DB : List<Category>  --> {}", allCategories);

		List<Manufacturer> allManufacturers = manufacturerService.getAllManufacturers();
		LOG.info("Get List of all manufacturers from DB : List<Manufacturer> --> {}", allManufacturers);

		List<Product> filteredProducts = productService.getFilteredProducts(productFilter);
		LOG.info("Get List of filtered products from DB : List<Product> --> {}", filteredProducts);

		int countProducts = productService.getFilteredProductsCount(productFilter);
		LOG.info("Get count of filtered products from DB : countProducts --> {}", countProducts);

		req.getSession().setAttribute(PRODUCT_FILTER_ATTRIBUTE, productFilter);
		req.getSession().setAttribute(ALL_CATEGORIES_ATTRIBUTE, allCategories);
		req.getSession().setAttribute(ALL_MANUFACTURERS_ATTRIBUTE, allManufacturers);
		req.getSession().setAttribute(FILTERED_PRODUCTS_ATTRIBUTE, filteredProducts);
		req.getSession().setAttribute(COUNT_PRODUCTS_ATTRIBUTE, countProducts);

		LOG.debug("Go to forward address --> " + ALL_PRODUCTS_PAGE);
		req.getRequestDispatcher(ALL_PRODUCTS_PAGE).forward(req, resp);
	}
}
