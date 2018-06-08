package com.epam.preprod.web.exctractor;

import com.epam.preprod.web.bean.Captcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.UUID;

public class CaptchaExtractor implements RequestExtractor<Captcha> {
	private static final Logger LOG = LoggerFactory.getLogger(CaptchaExtractor.class);

	private static final String CONFIRM_CAPTCHA_PARAMETER = "confirm-captcha";
	private static final String CAPTCHA_ID = "CaptchaId";

	@Override
	public Captcha extractFromRequest(HttpServletRequest req) {
		LOG.debug("CaptchaExtractor starts.");

		String captchaValue = req.getParameter(CONFIRM_CAPTCHA_PARAMETER);

		Captcha captcha = new Captcha(null, captchaValue, 0);
		String captchaId = req.getParameter(CAPTCHA_ID);

		if (Objects.nonNull(captchaId)) {
			UUID id = UUID.fromString(captchaId);
			captcha.setId(id);
		}
		LOG.info("Extracted captcha: --> {}", captcha);

		LOG.debug("CaptchaExtractor finished.");
		return captcha;
	}
}
