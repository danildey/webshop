package com.epam.preprod.service.captcha.handler;

import com.epam.preprod.web.bean.Captcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public class CaptchaMapCleaner extends Thread {
	private static final Logger LOG = LoggerFactory.getLogger(CaptchaMapCleaner.class);

	private long lifetime;
	private ConcurrentMap<UUID, Captcha> captchaMap;

	public CaptchaMapCleaner(ConcurrentMap<UUID, Captcha> captchaMap, long lifetime) {
		this.captchaMap = captchaMap;
		this.lifetime = lifetime;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(lifetime);
			} catch (InterruptedException e) {
				LOG.warn(" CaptchaMapCleaner interrupted.");
			}
			LOG.info("Cleaning captchaMap. ");
			removeDeadCaptcha();
		}
	}

	private void removeDeadCaptcha() {
		captchaMap.values().removeIf(captcha -> captcha.getLifetime() > new Date().getTime());
	}
}
