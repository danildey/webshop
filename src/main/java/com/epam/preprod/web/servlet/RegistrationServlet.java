package com.epam.preprod.web.servlet;

import com.epam.preprod.entity.User;
import com.epam.preprod.service.AvatarService;
import com.epam.preprod.service.UserService;
import com.epam.preprod.service.avatar.AvatarServiceImpl;
import com.epam.preprod.service.captcha.handler.CaptchaHandler;
import com.epam.preprod.validator.AvatarValidator;
import com.epam.preprod.validator.CaptchaValidator;
import com.epam.preprod.validator.RegistrationFormValidator;
import com.epam.preprod.validator.Validator;
import com.epam.preprod.web.bean.Avatar;
import com.epam.preprod.web.bean.Captcha;
import com.epam.preprod.web.bean.RegistrationForm;
import com.epam.preprod.web.exctractor.*;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@WebServlet("/register")
@MultipartConfig
public class RegistrationServlet extends HttpServlet {
	private static final Logger LOG = LoggerFactory.getLogger(RegistrationServlet.class);

	private static final String CAPTCHA_SERVLET = "/captcha";

	private static final String FIRST_NAME_SAVE = "firstName";
	private static final String LAST_NAME_SAVE = "lastName";
	private static final String EMAIL_SAVE = "email";
	private static final String USER_FIRST_NAME_PARAMETER = "user-fname";
	private static final String USER_LAST_NAME_PARAMETER = "user-lname";
	private static final String USER_EMAIL_PARAMETER = "user-email";

	private static final String ERRORS = "errors";
	private static final String USER_SERVICE = "userService";
	private static final String AVATAR_SERVICE = "avatarService";
	private static final String REGISTRATION_FORM_VALIDATOR = "registrationFormValidator";
	private static final String AVATAR_VALIDATOR = "avatarValidator";
	private static final String CAPTCHA_VALIDATOR = "captchaValidator";
	private static final String CAPTCHA_HANDLER = "captchaHandler";
	private static final String USER_EXIST_ERROR_MSG_PATH = "create-user-error-msg";
	private static final String USER_ERROR_MSG = "User with your email already exist";

	private static final String USER_REQUEST_EXTRACTOR = "userExtractor";
	private static final String REGISTRATION_FORM_REQUEST_EXTRACTOR = "registrationFormExtractor";
	private static final String CAPTCHA_REQUEST_EXTRACTOR = "captchaExtractor";
	private static final String AVATAR_REQUEST_EXTRACTOR = "avatarExtractor";

	private CaptchaHandler captchaHandler;
	private UserService userService;
	private AvatarService avatarService;

	private CaptchaValidator captchaValidator;
	private Validator<RegistrationForm> formValidator;
	private Validator<Avatar> avatarValidator;

	private RequestExtractor<User> userExtractor;
	private RequestExtractor<RegistrationForm> registrationFormExtractor;
	private RequestExtractor<Captcha> captchaExtractor;
	private RequestExtractor<Avatar> avatarExtractor;

	@Override
	public void init() {
		captchaHandler = (CaptchaHandler) getServletContext().getAttribute(CAPTCHA_HANDLER);
		userService = (UserService) getServletContext().getAttribute(USER_SERVICE);
		avatarService = (AvatarServiceImpl) getServletContext().getAttribute(AVATAR_SERVICE);

		userExtractor = (UserExtractor) getServletContext().getAttribute(USER_REQUEST_EXTRACTOR);
		registrationFormExtractor = (RegistrationFormExtractor) getServletContext().getAttribute(REGISTRATION_FORM_REQUEST_EXTRACTOR);
		captchaExtractor = (CaptchaExtractor) getServletContext().getAttribute(CAPTCHA_REQUEST_EXTRACTOR);
		avatarExtractor = (AvatarExtractor) getServletContext().getAttribute(AVATAR_REQUEST_EXTRACTOR);

		captchaValidator = (CaptchaValidator) getServletContext().getAttribute(CAPTCHA_VALIDATOR);
		formValidator = (RegistrationFormValidator) getServletContext().getAttribute(REGISTRATION_FORM_VALIDATOR);
		avatarValidator = (AvatarValidator) getServletContext().getAttribute(AVATAR_VALIDATOR);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOG.debug("doGet() called");
		LOG.debug("Go to forward address -->  {}", CAPTCHA_SERVLET);
		getServletContext().getRequestDispatcher(CAPTCHA_SERVLET).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		LOG.debug("doPost() called");

		RegistrationForm formBean = registrationFormExtractor.extractFromRequest(req);

		Avatar avatar = avatarExtractor.extractFromRequest(req);

		Captcha captcha = captchaExtractor.extractFromRequest(req);
		Captcha storedCaptcha = captchaHandler.getCaptcha(req);
		LOG.info("Get storedCaptcha from handler: --> {}", storedCaptcha);

		Map<String, String> errors = new HashMap<>();
		errors.putAll(formValidator.validate(formBean));
		errors.putAll(captchaValidator.validate(captcha, storedCaptcha));
		errors.putAll(avatarValidator.validate(avatar));

		if (errors.isEmpty()) {
			User user = userExtractor.extractFromRequest(req);
			if (userService.isUserExist(user.getEmail())) {
				LOG.info("User already exist : user --> {}", user);
				errors.put(USER_EXIST_ERROR_MSG_PATH, USER_ERROR_MSG);
				backToRegistration(req, resp, errors);
			} else {
				LOG.info("User can be created  : user --> {}", user);

				user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));
				LOG.info("Hashed user's password  : Password --> {}", user.getPassword());

				saveAvatar(avatar, user);

				userService.createUser(user);
				req.removeAttribute(ERRORS);
				LOG.debug("Go to redirect address -->  {}", req.getContextPath());
				resp.sendRedirect(req.getContextPath());
			}
		} else {
			backToRegistration(req, resp, errors);
		}
	}

	private void saveAvatar(Avatar avatar, User user) throws IOException {
		if (Objects.nonNull(avatar)) {
			LOG.info("Save user avatar with name : newName / avatar -->" + user.getEmail() + "/" + avatar);
			avatarService.saveAvatar(avatar, user.getEmail());
			LOG.info("Set avatar extension in userBean  : extension --> {}", avatar.getExtension());
			user.setUserAvatarExtension(avatar.getExtension());
		}
	}

	private void backToRegistration(HttpServletRequest req, HttpServletResponse resp, Map<String, String> errors) throws IOException {
		saveFieldValuesState(req);
		req.getSession().setAttribute(ERRORS, errors);
		LOG.info("Set the session attribute:" + ERRORS + " -->  {}", errors);

		String forward = req.getContextPath() + CAPTCHA_SERVLET;
		LOG.debug("Go to redirect address -->  {}", forward);
		resp.sendRedirect(forward);
	}

	private void saveFieldValuesState(HttpServletRequest req) {
		String userName = req.getParameter(USER_FIRST_NAME_PARAMETER);
		String userLastName = req.getParameter(USER_LAST_NAME_PARAMETER);
		String userEmail = req.getParameter(USER_EMAIL_PARAMETER);
		LOG.info("Set the session attribute: " + FIRST_NAME_SAVE + " -->  {}", userName);
		LOG.info("Set the session attribute: " + LAST_NAME_SAVE + " -->  {}", userLastName);
		LOG.info("Set the session attribute: " + EMAIL_SAVE + " -->  {}", userEmail);
		req.getSession().setAttribute(FIRST_NAME_SAVE, userName);
		req.getSession().setAttribute(LAST_NAME_SAVE, userLastName);
		req.getSession().setAttribute(EMAIL_SAVE, userEmail);
	}

}
