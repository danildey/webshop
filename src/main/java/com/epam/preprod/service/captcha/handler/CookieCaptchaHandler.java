package com.epam.preprod.service.captcha.handler;

import com.epam.preprod.web.bean.Captcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public class CookieCaptchaHandler extends MappedCaptchaHandler {
	private static final Logger LOG = LoggerFactory.getLogger(CookieCaptchaHandler.class);

	private static final String CAPTCHA_ID = "CaptchaId";

	public CookieCaptchaHandler(ConcurrentMap<UUID, Captcha> captchaMap) {
		super(captchaMap);
	}

	@Override
	public void saveCaptcha(HttpServletRequest req, HttpServletResponse resp, Captcha captcha) {
		captchaMap.put(captcha.getId(), captcha);
		Cookie cookie = new Cookie(CAPTCHA_ID, captcha.getId().toString());
		LOG.info("Add captcha to cookie: cookie -->  {}", cookie);
		resp.addCookie(cookie);
	}

	@Override
	public Captcha getCaptcha(HttpServletRequest req) {
		UUID captchaId = getCaptchaId(req.getCookies());
		LOG.info("Cookies captcha UUID: captchaId -->  {}", captchaId);
		return captchaMap.get(captchaId);
	}

	private UUID getCaptchaId(Cookie[] cookies) {
		if (Objects.nonNull(cookies)) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(CAPTCHA_ID)) {
					return UUID.fromString(cookie.getValue());
				}
			}
		}
		return null;
	}
}
