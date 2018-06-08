package com.epam.preprod.validator;

import com.epam.preprod.web.bean.Avatar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AvatarValidator implements Validator<Avatar> {
	private static final Logger LOG = LoggerFactory.getLogger(AvatarValidator.class);

	private static final String IMAGE_PATTERN = "((jpg|png|gif|bmp))$";

	private static final String AVATAR_EXTENSION_ERROR_MESSAGE = "Wrong image extension!";

	private static final String AVATAR_ERROR_MSG_PATH = "avatar-error-msg";


	@Override
	public Map<String, String> validate(Avatar avatar) {
		LOG.debug("AvatarValidator starts.");
		Map<String, String> errors = new HashMap<>();
		if (Objects.nonNull(avatar)) {

			errors = new HashMap<>();
			validateExtension(errors, avatar.getExtension());
		}
		LOG.debug("AvatarValidator finished.");
		return errors;
	}

	private void validateExtension(Map<String, String> errors, String image) {
		if (!image.matches(IMAGE_PATTERN)) {
			errors.put(AVATAR_ERROR_MSG_PATH, AVATAR_EXTENSION_ERROR_MESSAGE);
		}
	}
}
