package com.epam.preprod.validator;

import com.epam.preprod.web.bean.Captcha;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class CaptchaValidatorTest {
	private final UUID captchaId = UUID.randomUUID();
	private final String CAPTCHA_ERROR_MSG_PATH = "confirm-captcha-error-msg";

	@Mock
	private Captcha captcha;
	@Mock
	private Captcha storedCaptcha;

	private CaptchaValidator validator;

	@Before
	public void setUp() throws Exception {
		validator = new CaptchaValidator();

		doReturn("value").when(captcha).getValue();
		doReturn(1L).when(captcha).getLifetime();
		doReturn(captchaId).when(captcha).getId();

		doReturn("value").when(storedCaptcha).getValue();
		doReturn(1L).when(storedCaptcha).getLifetime();
		doReturn(captchaId).when(storedCaptcha).getId();
	}

	@Test
	public void shouldReturnEmptyMapIfCaptchaIsValid() {
		assertTrue(validator.validate(captcha, storedCaptcha).isEmpty());
	}

	@Test
	public void shouldReturnMapWithErrorWhenCaptchaIsNull() {
		captcha = null;
		assertTrue(validator.validate(captcha, storedCaptcha).containsKey(CAPTCHA_ERROR_MSG_PATH));
	}

	@Test
	public void shouldReturnMapWithErrorWhenStoredCaptchaIsNull() {
		storedCaptcha = null;
		assertTrue(validator.validate(captcha, storedCaptcha).containsKey(CAPTCHA_ERROR_MSG_PATH));
	}

	@Test
	public void shouldReturnMapWithErrorWhenCaptchaLifetimeBiggerThenStoredCaptchaLifetime() {
		doReturn(storedCaptcha.getLifetime() + 1L).when(captcha).getLifetime();
		assertTrue(validator.validate(captcha, storedCaptcha).containsKey(CAPTCHA_ERROR_MSG_PATH));
	}

	@Test
	public void shouldReturnMapWithErrorWhenCaptchaValuesIsDifferent() {
		doReturn("differentValue").when(storedCaptcha).getValue();
		assertTrue(validator.validate(captcha, storedCaptcha).containsKey(CAPTCHA_ERROR_MSG_PATH));
	}
}