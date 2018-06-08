package com.epam.preprod.web.exctractor;

import com.epam.preprod.web.bean.RegistrationForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class RegistrationFormExtractor implements RequestExtractor<RegistrationForm> {
	private static final Logger LOG = LoggerFactory.getLogger(RegistrationFormExtractor.class);

	private static final String USER_FIRST_NAME_PARAMETER = "user-fname";
	private static final String USER_LAST_NAME_PARAMETER = "user-lname";
	private static final String USER_EMAIL_PARAMETER = "user-email";
	private static final String USER_PASSWORD_PARAMETER = "user-password";
	private static final String CHECKBOX_PARAMETER = "checkbox";
	private static final String CONFIRM_PASSWORD_PARAMETER = "confirm-password";

	@Override
	public RegistrationForm extractFromRequest(HttpServletRequest req) {
		LOG.debug("RegistrationFormExtractor starts");

		RegistrationForm registrationForm = new RegistrationForm();
		registrationForm.setFirstName(req.getParameter(USER_FIRST_NAME_PARAMETER).trim());
		registrationForm.setLastName(req.getParameter(USER_LAST_NAME_PARAMETER).trim());
		registrationForm.setEmail(req.getParameter(USER_EMAIL_PARAMETER).trim());
		registrationForm.setCheckbox(req.getParameter(CHECKBOX_PARAMETER));
		registrationForm.setPassword(req.getParameter(USER_PASSWORD_PARAMETER));
		registrationForm.setConfirmPassword(req.getParameter(CONFIRM_PASSWORD_PARAMETER));
		LOG.info("Extracted Form: --> {}", registrationForm);

		LOG.debug("RegistrationFormExtractor finished.");

		return registrationForm;
	}
}
