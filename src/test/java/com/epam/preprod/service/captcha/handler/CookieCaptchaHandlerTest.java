package com.epam.preprod.service.captcha.handler;

import com.epam.preprod.web.bean.Captcha;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class CookieCaptchaHandlerTest {
	private final String CAPTCHA_ID = "CaptchaId";
	private final String CAPTCHA_VALUE = "value";
	private final long CAPTCHA_LIFETIME = 0L;
	private final UUID CAPTCHA_UUID = UUID.randomUUID();

	private CaptchaHandler handler;
	private Captcha captcha;

	private HttpServletResponse respMock;
	private HttpServletRequest reqMock;
	private ConcurrentMap captchaMapMock;
	private Cookie cookie;


	@Before
	public void setup() {
		captcha = new Captcha(CAPTCHA_UUID, CAPTCHA_VALUE, CAPTCHA_LIFETIME);
		cookie = mock(Cookie.class);
		Cookie[] cookies = {cookie};
		respMock = mock(HttpServletResponse.class);
		reqMock = mock(HttpServletRequest.class);
		captchaMapMock = mock(ConcurrentMap.class);

		when(captchaMapMock.get(any(UUID.class))).thenReturn(captcha);
		when(reqMock.getParameter(CAPTCHA_ID)).thenReturn(CAPTCHA_UUID.toString());
		when(reqMock.getCookies()).thenReturn(cookies);
		when(cookie.getName()).thenReturn(CAPTCHA_ID);
		when(cookie.getValue()).thenReturn(CAPTCHA_UUID.toString());

		handler = new CookieCaptchaHandler(captchaMapMock);
	}

	@Test
	public void shouldReadParameterFromRequestAndReturnCaptchaFromMapByParameter() {
		assertEquals(captcha, handler.getCaptcha(reqMock));
		verify(captchaMapMock, times(1)).get(CAPTCHA_UUID);
		verify(cookie, times(1)).getName();
		verify(cookie, times(1)).getValue();
	}

	@Test
	public void shouldReturnNullWhenCallGetCaptcha() {
		ConcurrentMap emptyCaptchaMapMock = mock(ConcurrentMap.class);
		HttpServletRequest emptyReqMock = mock(HttpServletRequest.class);
		handler = new CookieCaptchaHandler(emptyCaptchaMapMock);
		assertNull(handler.getCaptcha(emptyReqMock));
	}

	@Test
	public void shouldPutCaptchaIntoMapAndAddCookie() {
		handler.saveCaptcha(reqMock, respMock, captcha);
		verify(captchaMapMock, times(1)).put(CAPTCHA_UUID, captcha);
		verify(respMock, times(1)).addCookie(any(Cookie.class));
	}
}