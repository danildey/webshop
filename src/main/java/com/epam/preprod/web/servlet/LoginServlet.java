package com.epam.preprod.web.servlet;

import com.epam.preprod.entity.User;
import com.epam.preprod.service.UserService;
import com.epam.preprod.validator.LoginFormValidator;
import com.epam.preprod.validator.Validator;
import com.epam.preprod.web.bean.LoginForm;
import com.epam.preprod.web.exctractor.LoginFormExtractor;
import com.epam.preprod.web.exctractor.RequestExtractor;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final Logger LOG = LoggerFactory.getLogger(LoginServlet.class);

	private static final String LOGIN_PAGE = "/loginPage";
	private static final String LOGIN_SERVLET = "/login";

	private static final String USER_BEAN = "userBean";

	private static final String USER_SERVICE = "userService";
	private static final String LOGIN_FORM_REQUEST_EXTRACTOR = "loginFormExtractor";
	private static final String LOGIN_FORM_VALIDATOR = "loginFormValidator";

	private static final String ERRORS = "errors";
	private final static String ACCESS_ERROR_PATH = "access-error-msg";
	private final static String ACCESS_ERROR_MSG = "Wrong email or password!";
	private final static String USER_DOES_NOT_EXIST_MSG = "User with this email does not exist!";

	private UserService userService;
	private RequestExtractor<LoginForm> loginFormRequestExtractor;

	private Validator<LoginForm> formValidator;


	@Override
	public void init() {
		userService = (UserService) getServletContext().getAttribute(USER_SERVICE);

		loginFormRequestExtractor = (LoginFormExtractor) getServletContext().getAttribute(LOGIN_FORM_REQUEST_EXTRACTOR);
		formValidator = (LoginFormValidator) getServletContext().getAttribute(LOGIN_FORM_VALIDATOR);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOG.debug("doGet() called");
		LOG.debug("Go to forward address --> " + LOGIN_PAGE);
		getServletContext().getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOG.debug("doPost() called");
		LoginForm loginForm = loginFormRequestExtractor.extractFromRequest(req);
		Map<String, String> errors = formValidator.validate(loginForm);
		if (errors.isEmpty()) {
			User user = userService.getUser(loginForm.getEmail());
			LOG.info("Get user by email:" + loginForm.getEmail() + " -->  {}", user);
			checkUserAccess(req, resp, loginForm.getPassword(), errors, user);
		} else {
			backToLogin(req, resp, errors);
		}
	}

	private void checkUserAccess(HttpServletRequest req, HttpServletResponse resp, String password, Map<String, String> errors, User user) throws IOException {
		if (Objects.nonNull(user)) {

			if (BCrypt.checkpw(password, user.getPassword())) {
				LOG.info("User sign up: --> {}", user);
				req.getSession().setAttribute(USER_BEAN, user);
				resp.sendRedirect(req.getContextPath());
			} else {
				LOG.info("The entered password is not identical to the stored password. ");
				errors.put(ACCESS_ERROR_PATH, ACCESS_ERROR_MSG);
				backToLogin(req, resp, errors);
			}
		} else {
			LOG.info("User not exist.");
			errors.put(ACCESS_ERROR_PATH, USER_DOES_NOT_EXIST_MSG);
			backToLogin(req, resp, errors);
		}
	}

	private void backToLogin(HttpServletRequest req, HttpServletResponse resp, Map<String, String> errors) throws IOException {
		req.getSession().setAttribute(ERRORS, errors);
		LOG.info("Set the session attribute:" + ERRORS + " -->  {}", errors);
		String forward = req.getContextPath() + LOGIN_SERVLET;
		LOG.debug("Go to redirect address -->  {}", forward);
		resp.sendRedirect(forward);
	}
}
