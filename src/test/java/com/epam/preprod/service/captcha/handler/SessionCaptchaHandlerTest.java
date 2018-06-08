package com.epam.preprod.service.captcha.handler;

import com.epam.preprod.web.bean.Captcha;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SessionCaptchaHandlerTest {
	private final String CAPTCHA = "captcha";
	private CaptchaHandler handler;

	private Captcha captcha;
	private HttpServletResponse respMock;
	private HttpServletRequest reqMock;
	private HttpSession sessionMock;


	@Before
	public void setup() {
		respMock = mock(HttpServletResponse.class);
		reqMock = mock(HttpServletRequest.class);
		sessionMock = mock(HttpSession.class);
		captcha = mock(Captcha.class);

		when(sessionMock.getAttribute(CAPTCHA)).thenReturn(captcha);
		when(reqMock.getSession()).thenReturn(sessionMock);

		handler = new SessionCaptchaHandler();
	}

	@Test
	public void shouldGetCaptchaFromSessionAttributeAndRemoveCaptchaAttribute() {
		assertEquals(captcha, handler.getCaptcha(reqMock));
		verify(sessionMock, times(1)).getAttribute(CAPTCHA);
		verify(sessionMock, times(1)).removeAttribute(CAPTCHA);
	}

	@Test
	public void shouldPutCaptchaIntoSessionAttribute() {
		handler.saveCaptcha(reqMock, respMock, captcha);
		verify(sessionMock, times(1)).setAttribute(CAPTCHA, captcha);
	}
}