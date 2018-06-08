package com.epam.preprod.service.captcha.handler;

import com.epam.preprod.web.bean.Captcha;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class ApplicationCaptchaHandlerTest {
	private final String CAPTCHA_VALUE = "value";
	private final long CAPTCHA_LIFETIME = 0L;
	private final UUID CAPTCHA_UUID = UUID.randomUUID();
	private final String CAPTCHA_ID = "CaptchaId";

	private CaptchaHandler handler;
	private Captcha captcha;

	private HttpServletResponse respMock;
	private HttpServletRequest reqMock;
	private ConcurrentMap captchaMapMock;

	@Before
	public void setup() {
		captcha = new Captcha(CAPTCHA_UUID, CAPTCHA_VALUE, CAPTCHA_LIFETIME);
		respMock = mock(HttpServletResponse.class);
		reqMock = mock(HttpServletRequest.class);
		captchaMapMock = mock(ConcurrentMap.class);

		when(captchaMapMock.get(CAPTCHA_UUID)).thenReturn(captcha);
		when(reqMock.getParameter(CAPTCHA_ID)).thenReturn(CAPTCHA_UUID.toString());

		handler = new ApplicationCaptchaHandler(captchaMapMock);
	}

	@Test
	public void shouldReadParameterFromRequestAndGetCaptchaFromMapByParameter() {
		assertEquals(captcha, handler.getCaptcha(reqMock));
		verify(reqMock, times(1)).getParameter(CAPTCHA_ID);
		verify(captchaMapMock, times(1)).get(CAPTCHA_UUID);
	}

	@Test
	public void shouldReturnNullWhenCallGetCaptcha() {
		ConcurrentMap emptyCaptchaMapMock = mock(ConcurrentMap.class);
		HttpServletRequest emptyReqMock = mock(HttpServletRequest.class);
		handler = new ApplicationCaptchaHandler(emptyCaptchaMapMock);
		assertNull(handler.getCaptcha(emptyReqMock));
	}

	@Test
	public void shouldPutCaptchaIntoMapAndRequestAttribute() {
		handler.saveCaptcha(reqMock, respMock, captcha);
		verify(captchaMapMock, times(1)).put(CAPTCHA_UUID, captcha);
		verify(reqMock, times(1)).setAttribute(CAPTCHA_ID, CAPTCHA_UUID);
	}
}