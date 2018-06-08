package com.epam.preprod.web.servlet;

import com.epam.preprod.entity.User;
import com.epam.preprod.service.AvatarService;
import com.epam.preprod.service.UserService;
import com.epam.preprod.service.captcha.handler.CaptchaHandler;
import com.epam.preprod.validator.CaptchaValidator;
import com.epam.preprod.validator.Validator;
import com.epam.preprod.web.bean.Avatar;
import com.epam.preprod.web.bean.Captcha;
import com.epam.preprod.web.bean.RegistrationForm;
import com.epam.preprod.web.exctractor.RequestExtractor;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServletTest {

	private final String CAPTCHA_SERVLET = "/captcha";
	private final String CONTEXT_PATH = "webapp";

	private final String ERRORS = "errors";
	private final String USER_REQUEST_EXTRACTOR = "userExtractor";
	private final String REGISTRATION_FORM_REQUEST_EXTRACTOR = "registrationFormExtractor";
	private final String CAPTCHA_REQUEST_EXTRACTOR = "captchaExtractor";
	private final String AVATAR_REQUEST_EXTRACTOR = "avatarExtractor";

	private Map<String, String> mapWithErrors;

	@Mock(name = "userExtractor")
	private RequestExtractor<User> userExtractorMock;
	@Mock(name = "registrationFormExtractor")
	private RequestExtractor<RegistrationForm> registrationFormExtractorMock;
	@Mock(name = "captchaExtractor")
	private RequestExtractor<Captcha> captchaExtractorMock;
	@Mock(name = "avatarExtractor")
	private RequestExtractor<Avatar> avatarExtractor;

	@Mock
	private Captcha captchaMock;
	@Mock
	private Avatar avatarMock;
	@Mock
	private static Logger log;
	@Mock
	private RegistrationForm registrationFormMock;
	@Mock
	private User userMock;
	@Mock
	private CaptchaValidator captchaValidator;
	@Mock
	private Validator<RegistrationForm> formValidator;
	@Mock
	private Validator<Avatar> avatarValidator;

	@Mock
	private CaptchaHandler captchaHandler;
	@Mock
	private UserService userService;
	@Mock
	private AvatarService avatarService;
	@Mock
	private RequestDispatcher requestDispatcherMock;
	@Mock
	private HttpServletResponse respMock;
	@Mock
	private HttpServletRequest reqMock;
	@Mock
	private HttpSession sessionMock;
	@Mock
	private ServletContext servletContextMock;
	@Mock
	private ServletConfig servletConfigMock;

	@InjectMocks
	private RegistrationServlet servlet = new RegistrationServlet();

	@Before
	public void setUp() throws Exception {
		when(servletContextMock.getAttribute(USER_REQUEST_EXTRACTOR)).thenReturn(userExtractorMock);
		when(servletContextMock.getAttribute(REGISTRATION_FORM_REQUEST_EXTRACTOR)).thenReturn(registrationFormExtractorMock);
		when(servletContextMock.getAttribute(CAPTCHA_REQUEST_EXTRACTOR)).thenReturn(captchaExtractorMock);
		when(servletContextMock.getAttribute(AVATAR_REQUEST_EXTRACTOR)).thenReturn(avatarExtractor);

		when(servletContextMock.getRequestDispatcher(CAPTCHA_SERVLET)).thenReturn(requestDispatcherMock);
		when(servletConfigMock.getServletContext()).thenReturn(servletContextMock);

		when(userExtractorMock.extractFromRequest(reqMock)).thenReturn(userMock);
		when(registrationFormExtractorMock.extractFromRequest(reqMock)).thenReturn(registrationFormMock);
		when(captchaExtractorMock.extractFromRequest(reqMock)).thenReturn(captchaMock);
		when(avatarExtractor.extractFromRequest(reqMock)).thenReturn(avatarMock);

		when(reqMock.getSession()).thenReturn(sessionMock);

		when(userMock.getEmail()).thenReturn("test@mail.ru");

		mapWithErrors = new HashMap<>();
		when(reqMock.getContextPath()).thenReturn(CONTEXT_PATH);
		when(captchaValidator.validate(any(Captcha.class), any(Captcha.class))).thenReturn(Collections.emptyMap());
		when(formValidator.validate(any(RegistrationForm.class))).thenReturn(Collections.emptyMap());
		when(avatarValidator.validate(any(Avatar.class))).thenReturn(Collections.emptyMap());
		when(userService.isUserExist(anyString())).thenReturn(false);
	}


	@Test
	public void shouldRemoveErrorsMessagesFromSessionAndForwardOnCaptchaServlet() throws Exception {
		servlet.doGet(reqMock, respMock);
		verify(servletContextMock, times(1)).getRequestDispatcher(CAPTCHA_SERVLET);
		verify(requestDispatcherMock, times(1)).forward(reqMock, respMock);
	}

	@Test
	public void shouldSendRedirectOnMainPageIfAllIsValid() throws Exception {
		servlet.doPost(reqMock, respMock);
		verify(respMock, times(1)).sendRedirect(CONTEXT_PATH);
	}

	@Test
	public void shouldAddCaptchaErrorsMessagesAndRedirectOnCaptchaServletIfCaptchaIsNotValid() throws Exception {
		mapWithErrors.put("captchaError", "errorMsg");
		doReturn(mapWithErrors).when(captchaValidator).validate(any(Captcha.class), any(Captcha.class));
		servlet.doPost(reqMock, respMock);
		verify(sessionMock).setAttribute(ERRORS, mapWithErrors);
		verify(respMock, times(1)).sendRedirect(CONTEXT_PATH + CAPTCHA_SERVLET);
	}

	@Test
	public void shouldAddFormErrorsMessagesAndRedirectOnCaptchaServletRegistrationIfFormIsNotValid() throws Exception {
		mapWithErrors.put("formError", "errorMsg");
		doReturn(mapWithErrors).when(formValidator).validate(registrationFormMock);
		servlet.doPost(reqMock, respMock);
		verify(sessionMock).setAttribute(ERRORS, mapWithErrors);
		verify(respMock, times(1)).sendRedirect(CONTEXT_PATH + CAPTCHA_SERVLET);
	}

	@Test
	public void shouldRedirectOnCaptchaServletIfUserIsNull() throws Exception {
		doReturn(true).when(userService).isUserExist(anyString());
		servlet.doPost(reqMock, respMock);
		verify(respMock, times(1)).sendRedirect(CONTEXT_PATH + CAPTCHA_SERVLET);
	}
}