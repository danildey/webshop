package com.epam.preprod.service.captcha.handler;

import com.epam.preprod.web.bean.Captcha;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public abstract class MappedCaptchaHandler implements CaptchaHandler {
	protected ConcurrentMap<UUID, Captcha> captchaMap;

	public MappedCaptchaHandler(ConcurrentMap<UUID, Captcha> captchaMap) {
		this.captchaMap = captchaMap;
	}
}
