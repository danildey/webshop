package com.epam.preprod.validator;

import com.epam.preprod.web.bean.RegistrationForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegistrationFormValidator implements Validator<RegistrationForm> {
	private static final Logger LOG = LoggerFactory.getLogger(RegistrationFormValidator.class);

	private static final String LETTER_REGEX = "^[A-Za-z\\u0400-\\u04FF]+$";
	private static final String EMAIL_REGEX = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
	private static final String PASSWORD_REGEX = "^[a-zA-Z\\d\\s\\u0400-\\u04FF]{8,}$";

	private final static String FIRST_NAME_ERROR_MSG_PATH = "fname-error-msg";
	private final static String LAST_NAME_ERROR_MSG_PATH = "lname-error-msg";
	private final static String EMAIL_ERROR_MSG_PATH = "email-error-msg";
	private final static String PASSWORD_ERROR_MSG_PATH = "password-error-msg";
	private final static String CONFIRM_PASSWORD_ERROR_MSG_PATH = "password-confirm-error-msg";
	private final static String CHECKBOX_ERROR_MSG_PATH = "checkbox-error-msg";

	private final static String FIRST_NAME_ERROR_MSG = "First name must have alphabet characters only!";
	private final static String LAST_NAME_ERROR_MSG = "Last name must have alphabet characters only!";
	private final static String EMAIL_ERROR_MSG = "You have entered an invalid email address!";
	private final static String PASSWORD_ERROR_MSG = "Password should contains more then 6 symbol!";
	private final static String CONFIRM_PASSWORD_ERROR_MSG = "Password confirm should be equels with Password !";
	private final static String EMPTY_CHECKBOX_ERROR_MSG = "Wrong checkbox !";


	@Override
	public Map<String, String> validate(RegistrationForm formBean) {
		LOG.debug("RegistrationFormValidator starts.");
		Map<String, String> errors = new HashMap<>();
		validateFirstName(formBean.getFirstName(), errors);
		validateLastName(formBean.getLastName(), errors);
		validateEmail(formBean.getEmail(), errors);
		validatePassword(formBean.getPassword(), errors);
		validatePasswordConfirm(formBean.getConfirmPassword(), formBean.getPassword(), errors);
		validateCheckbox(formBean.getCheckbox(), errors);
		LOG.debug("RegistrationFormValidator finished.");

		return errors;
	}

	private void validateFirstName(String firstName, Map<String, String> errors) {
		if (!firstName.matches(LETTER_REGEX)) {
			errors.put(FIRST_NAME_ERROR_MSG_PATH, FIRST_NAME_ERROR_MSG);
		}
	}

	private void validateLastName(String lastName, Map<String, String> errors) {
		if (!lastName.matches(LETTER_REGEX)) {
			errors.put(LAST_NAME_ERROR_MSG_PATH, LAST_NAME_ERROR_MSG);
		}
	}

	private void validateEmail(String email, Map<String, String> errors) {
		if (!email.matches(EMAIL_REGEX)) {
			errors.put(EMAIL_ERROR_MSG_PATH, EMAIL_ERROR_MSG);
		}
	}

	private void validatePassword(String pass, Map<String, String> errors) {
		if (!pass.matches(PASSWORD_REGEX)) {
			errors.put(PASSWORD_ERROR_MSG_PATH, PASSWORD_ERROR_MSG);
		}
	}

	private void validatePasswordConfirm(String pass, String confirmWith, Map<String, String> errors) {
		if (pass.isEmpty() || !pass.equals(confirmWith)) {
			errors.put(CONFIRM_PASSWORD_ERROR_MSG_PATH, CONFIRM_PASSWORD_ERROR_MSG);
		}
	}

	private void validateCheckbox(String checkbox, Map<String, String> errors) {
		if (Objects.nonNull(checkbox) && !"on".equals(checkbox)) {
			errors.put(CHECKBOX_ERROR_MSG_PATH, EMPTY_CHECKBOX_ERROR_MSG);
		}
	}
}
