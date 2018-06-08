package com.epam.preprod.service.captcha;

import com.epam.preprod.service.CaptchaService;
import com.epam.preprod.web.bean.Captcha;
import com.github.cage.Cage;

import java.util.Base64;
import java.util.Random;
import java.util.UUID;

public class RandomNumberCaptchaService implements CaptchaService {
	private final int CAPTCHA_LENGTH = 5;
	private final long lifetime;
	private final Cage cage;

	public RandomNumberCaptchaService(Cage cage, long lifetime) {
		this.lifetime = lifetime;
		this.cage = cage;

	}

	@Override
	public String drawCaptchaInBase64(Captcha captcha) {
		byte[] imageBytes = cage.draw(captcha.getValue());
		return Base64.getEncoder().encodeToString(imageBytes);
	}

	@Override
	public Captcha generateCaptcha() {
		String generatedValue = generateRandomNumber(CAPTCHA_LENGTH);
		return new Captcha(UUID.randomUUID(), generatedValue, lifetime);
	}

	private String generateRandomNumber(int charLength) {
		return String.valueOf(charLength < 1 ? 0 : new Random()
				.nextInt((9 * (int) Math.pow(10, charLength - 1)) - 1)
				+ (int) Math.pow(10, charLength - 1));
	}
}
