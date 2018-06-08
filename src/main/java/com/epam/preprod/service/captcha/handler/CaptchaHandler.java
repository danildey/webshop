package com.epam.preprod.service.captcha.handler;

import com.epam.preprod.web.bean.Captcha;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CaptchaHandler {

	void saveCaptcha(HttpServletRequest req, HttpServletResponse resp, Captcha captcha);

	Captcha getCaptcha(HttpServletRequest req);
}
