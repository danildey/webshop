package com.epam.preprod.validator;

import com.epam.preprod.web.bean.LoginForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class LoginFormValidator implements Validator<LoginForm> {
	private static final Logger LOG = LoggerFactory.getLogger(LoginFormValidator.class);

	private static final String EMAIL_REGEX = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
	private static final String PASSWORD_REGEX = "^[a-zA-Z\\d\\s\\u0400-\\u04FF]{8,}$";

	private final static String PASSWORD_ERROR_MSG_PATH = "password-error-msg";
	private final static String EMAIL_ERROR_MSG_PATH = "email-error-msg";

	private final static String PASSWORD_ERROR_MSG = "Password should contains more then 6 symbol!";
	private final static String EMAIL_ERROR_MSG = "Invalid email address signature!";


	@Override
	public Map<String, String> validate(LoginForm loginForm) {
		LOG.debug("LoginFormValidator starts.");

		Map<String, String> errors = new HashMap<>();
		validateEmail(errors, loginForm.getEmail());
		validatePassword(errors, loginForm.getPassword());

		LOG.debug("LoginFormValidator finished.");
		return errors;
	}

	private void validatePassword(Map<String, String> errors, String pass) {
		if (!pass.matches(PASSWORD_REGEX)) {
			errors.put(PASSWORD_ERROR_MSG_PATH, PASSWORD_ERROR_MSG);
		}
	}

	private void validateEmail(Map<String, String> errors, String email) {
		if (!email.matches(EMAIL_REGEX)) {
			errors.put(EMAIL_ERROR_MSG_PATH, EMAIL_ERROR_MSG);
		}
	}
}
