package com.epam.preprod.service.captcha.handler;

import com.epam.preprod.web.bean.Captcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;


public class ApplicationCaptchaHandler extends MappedCaptchaHandler {
	private static final Logger LOG = LoggerFactory.getLogger(ApplicationCaptchaHandler.class);

	private static final String CAPTCHA_ID = "CaptchaId";

	public ApplicationCaptchaHandler(ConcurrentMap<UUID, Captcha> captchaMap) {
		super(captchaMap);
	}

	@Override
	public void saveCaptcha(HttpServletRequest req, HttpServletResponse resp, Captcha captcha) {
		captchaMap.put(captcha.getId(), captcha);
		LOG.info("Save captcha in map: -->  {}", captcha);
		req.setAttribute(CAPTCHA_ID, captcha.getId());
		LOG.info("Set the session attribute: " + CAPTCHA_ID + " --> " + captcha.getId());
	}

	@Override
	public Captcha getCaptcha(HttpServletRequest req) {
		String id = req.getParameter(CAPTCHA_ID);
		LOG.trace("Request parameter: " + CAPTCHA_ID + " --> " + id);
		UUID captchaId = null;
		if (Objects.nonNull(id) && !id.isEmpty()) {
			captchaId = UUID.fromString(id);
		}
		return captchaMap.get(captchaId);
	}
}
