package com.epam.preprod.validator;

import com.epam.preprod.web.bean.Captcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CaptchaValidator {
	private static final Logger LOG = LoggerFactory.getLogger(CaptchaValidator.class);

	private static final String CAPTCHA_NULL_ERROR_MSG = "Captcha can not be Empty!";
	private static final String CAPTCHA_TIMEOUT_ERROR_MSG = "Captcha timeout!";
	private static final String CAPTCHA_VALUE_ERROR_MSG = "Wrong Captcha!";

	private static final String CAPTCHA_ERROR_MSG_PATH = "confirm-captcha-error-msg";

	public Map<String, String> validate(Captcha captcha, Captcha storedCaptcha) {
		LOG.debug("CaptchaValidator starts.");

		Map<String, String> errors = new HashMap<>();
		if (Objects.nonNull(captcha) && Objects.nonNull(storedCaptcha)) {
			validateCaptchaNotEqual(captcha, storedCaptcha, errors);
			validateCaptchaTimeout(captcha, storedCaptcha, errors);
		} else {
			errors.put(CAPTCHA_ERROR_MSG_PATH, CAPTCHA_NULL_ERROR_MSG);
		}
		LOG.debug("CaptchaValidator finished.");

		return errors;
	}

	private void validateCaptchaTimeout(Captcha captcha, Captcha storedCaptcha, Map<String, String> errors) {
		if (captcha.getLifetime() > (storedCaptcha.getLifetime())) {
			errors.put(CAPTCHA_ERROR_MSG_PATH, CAPTCHA_TIMEOUT_ERROR_MSG);
		}
	}

	private void validateCaptchaNotEqual(Captcha captcha, Captcha storedCaptcha, Map<String, String> errors) {
		if (!captcha.getValue().equals(storedCaptcha.getValue())) {
			errors.put(CAPTCHA_ERROR_MSG_PATH, CAPTCHA_VALUE_ERROR_MSG);
		}
	}
}
