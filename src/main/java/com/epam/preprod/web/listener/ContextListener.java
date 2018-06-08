package com.epam.preprod.web.listener;

import com.epam.preprod.db.dao.UserDAO;
import com.epam.preprod.db.dao.impl.MySQLUserDAO;
import com.epam.preprod.db.dao.tranaction.TransactionManager;
import com.epam.preprod.db.repository.Repository;
import com.epam.preprod.db.repository.mysql.MySqlCategoryRepository;
import com.epam.preprod.db.repository.mysql.MySqlManufacturerRepository;
import com.epam.preprod.db.repository.mysql.MySqlProductRepository;
import com.epam.preprod.entity.Category;
import com.epam.preprod.entity.Manufacturer;
import com.epam.preprod.entity.Product;
import com.epam.preprod.entity.User;
import com.epam.preprod.service.*;
import com.epam.preprod.service.avatar.AvatarServiceImpl;
import com.epam.preprod.service.captcha.RandomNumberCaptchaService;
import com.epam.preprod.service.captcha.handler.*;
import com.epam.preprod.service.dbservice.DBCategoryService;
import com.epam.preprod.service.dbservice.DBManufacturerService;
import com.epam.preprod.service.dbservice.DBProductService;
import com.epam.preprod.service.dbservice.DBUserService;
import com.epam.preprod.validator.*;
import com.epam.preprod.web.bean.*;
import com.epam.preprod.web.exctractor.*;
import com.github.cage.Cage;
import com.github.cage.GCage;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@WebListener
public class ContextListener implements ServletContextListener {
	private static final Logger LOG = LoggerFactory.getLogger(ContextListener.class);

	private static final String LOG_PROPERTIES = "log4j.properties";

	private static final String USER_SERVICE = "userService";
	private static final String PRODUCT_SERVICE = "productService";
	private static final String CATEGORY_SERVICE = "categoryService";
	private static final String MANUFACTURER_SERVICE = "manufacturerService";
	private static final String AVATAR_SERVICE = "avatarService";
	private static final String REGISTRATION_FORM_VALIDATOR = "registrationFormValidator";
	private static final String LOGIN_FORM_VALIDATOR = "loginFormValidator";
	private static final String AVATAR_VALIDATOR = "avatarValidator";
	private static final String CAPTCHA_VALIDATOR = "captchaValidator";
	private static final String CAPTCHA_SERVICE = "captchaService";
	private static final String CAPTCHA_HANDLER = "captchaHandler";
	private static final String CAPTCHA_HANDLER_TYPE = "captchaHandler";
	private static final String SESSION_CAPTCHA_HANDLER = "sessionCaptchaHandler";
	private static final String COOKIE_CAPTCHA_HANDLER = "cookieCaptchaHandler";
	private static final String APP_CAPTCHA_HANDLER = "appCaptchaHandler";
	private static final String USER_REQUEST_EXTRACTOR = "userExtractor";
	private static final String PRODUCT_FILTER_BEAN_EXTRACTOR = "filterBeanExtractor";
	private static final String AVATAR_REQUEST_EXTRACTOR = "avatarExtractor";
	private static final String REGISTRATION_FORM_REQUEST_EXTRACTOR = "registrationFormExtractor";
	private static final String LOGIN_FORM_REQUEST_EXTRACTOR = "loginFormExtractor";
	private static final String CAPTCHA_REQUEST_EXTRACTOR = "captchaExtractor";

	private final long ONE_MINUTE = 60 * 1000;

	private File avatarsFolder;

	private Cage cage;
	private CaptchaHandler handler;
	private CaptchaService service;
	private Map<String, CaptchaHandler> handlerMap;
	private ConcurrentMap<UUID, Captcha> captchaMap;

	private Repository<Product> productRepository;
	private Repository<Category> categoryRepository;
	private Repository<Manufacturer> manufacturerRepository;

	private UserDAO userDAO;
	private AvatarService avatarService;
	private UserService userService;
	private ProductService productService;
	private CategoryService categoryService;
	private ManufacturerService manufacturerService;

	private Validator<RegistrationForm> registrationFormValidator;
	private Validator<LoginForm> loginFormValidator;
	private Validator<Avatar> avatarValidator;
	private CaptchaValidator captchaValidator;

	private RequestExtractor<User> userExtractor;
	private RequestExtractor<RegistrationForm> registrationFormExtractor;
	private RequestExtractor<LoginForm> loginFormExtractor;
	private RequestExtractor<Captcha> captchaExtractor;
	private RequestExtractor<Avatar> avatarExtractor;
	private RequestExtractor<ProductFilter> productFilterBeanExtractor;

	private DataSource dataSource;
	private TransactionManager transactionManager;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		cage = new GCage();
		captchaMap = new ConcurrentHashMap<>();
		ServletContext context = sce.getServletContext();

		initLog4j(context);
		initDataSource();

		initRepositories();

		initFormValidators(context);
		initCaptchaValidator(context);
		initAvatarValidator(context);

		initHandler(context);
		initUserService(context);
		initAvatarService(context);
		initCaptchaService(context);
		initServices(context);

		initExtractors(context);
	}

	private void initRepositories() {
		productRepository = new MySqlProductRepository();
		categoryRepository = new MySqlCategoryRepository();
		manufacturerRepository = new MySqlManufacturerRepository();
	}


	private void initServices(ServletContext context) {
		productService = new DBProductService(transactionManager, productRepository);
		categoryService = new DBCategoryService(transactionManager, categoryRepository);
		manufacturerService = new DBManufacturerService(transactionManager, manufacturerRepository);

		context.setAttribute(PRODUCT_SERVICE, productService);
		context.setAttribute(CATEGORY_SERVICE, categoryService);
		context.setAttribute(MANUFACTURER_SERVICE, manufacturerService);

	}

	private void initAvatarValidator(ServletContext context) {
		avatarValidator = new AvatarValidator();
		context.setAttribute(AVATAR_VALIDATOR, avatarValidator);
	}

	private void initDataSource() {
		Context envContext;
		try {
			envContext = (Context) new InitialContext().lookup("java:comp/env");
			dataSource = (DataSource) envContext.lookup("jdbc/root");
		} catch (NamingException e) {
			LOG.warn("DataSource was not initialize.", e);
		}
	}

	private void initLog4j(ServletContext context) {
		URL log4jUrl = ContextListener.class.getClassLoader().getResource(LOG_PROPERTIES);
		PropertyConfigurator.configure(log4jUrl);
	}

	private void initExtractors(ServletContext context) {
		userExtractor = new UserExtractor();
		registrationFormExtractor = new RegistrationFormExtractor();
		loginFormExtractor = new LoginFormExtractor();
		captchaExtractor = new CaptchaExtractor();
		avatarExtractor = new AvatarExtractor();
		productFilterBeanExtractor = new ProductFilterBeanExtractor();

		context.setAttribute(PRODUCT_FILTER_BEAN_EXTRACTOR, productFilterBeanExtractor);
		context.setAttribute(USER_REQUEST_EXTRACTOR, userExtractor);
		context.setAttribute(REGISTRATION_FORM_REQUEST_EXTRACTOR, registrationFormExtractor);
		context.setAttribute(LOGIN_FORM_REQUEST_EXTRACTOR, loginFormExtractor);
		context.setAttribute(CAPTCHA_REQUEST_EXTRACTOR, captchaExtractor);
		context.setAttribute(AVATAR_REQUEST_EXTRACTOR, avatarExtractor);
	}

	private void initFormValidators(ServletContext context) {
		registrationFormValidator = new RegistrationFormValidator();
		context.setAttribute(REGISTRATION_FORM_VALIDATOR, registrationFormValidator);
		loginFormValidator = new LoginFormValidator();
		context.setAttribute(LOGIN_FORM_VALIDATOR, loginFormValidator);

	}

	private void initCaptchaValidator(ServletContext context) {
		captchaValidator = new CaptchaValidator();
		context.setAttribute(CAPTCHA_VALIDATOR, captchaValidator);
	}

	private void initHandler(ServletContext context) {
		handlerMap = new HashMap<>();
		handlerMap.put(SESSION_CAPTCHA_HANDLER, new SessionCaptchaHandler());
		handlerMap.put(COOKIE_CAPTCHA_HANDLER, new CookieCaptchaHandler(captchaMap));
		handlerMap.put(APP_CAPTCHA_HANDLER, new ApplicationCaptchaHandler(captchaMap));
		String handlerName = context.getInitParameter(CAPTCHA_HANDLER_TYPE);
		handler = handlerMap.get(handlerName);
		context.setAttribute(CAPTCHA_HANDLER, handler);
		if (handler instanceof MappedCaptchaHandler) {
			new CaptchaMapCleaner(captchaMap, ONE_MINUTE).start();
		}
	}

	private void initCaptchaService(ServletContext context) {
		service = new RandomNumberCaptchaService(cage, ONE_MINUTE);
		context.setAttribute(CAPTCHA_SERVICE, service);
	}

	private void initUserService(ServletContext context) {
		transactionManager = new TransactionManager(dataSource);
		userDAO = new MySQLUserDAO();
		List<User> userList = new ArrayList<>();
		userService = new DBUserService(transactionManager, userDAO);
		context.setAttribute(USER_SERVICE, userService);
	}

	private void initAvatarService(ServletContext context) {
		avatarsFolder = new File(context.getInitParameter("avatar.location"));
		avatarService = new AvatarServiceImpl(avatarsFolder);
		context.setAttribute(AVATAR_SERVICE, avatarService);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}