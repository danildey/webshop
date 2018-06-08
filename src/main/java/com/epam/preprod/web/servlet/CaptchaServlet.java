package com.epam.preprod.web.servlet;

import com.epam.preprod.service.CaptchaService;
import com.epam.preprod.service.captcha.handler.CaptchaHandler;
import com.epam.preprod.web.bean.Captcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/captcha")
public class CaptchaServlet extends HttpServlet {
	private static final Logger LOG = LoggerFactory.getLogger(CaptchaServlet.class);

	private static final String REGISTER_PAGE = "/registerPage";
	private static final String CAPTCHA_IMAGE = "captchaImage";
	private static final String CAPTCHA_SERVICE = "captchaService";
	private static final String CAPTCHA_HANDLER = "captchaHandler";

	private CaptchaService captchaService;
	private CaptchaHandler captchaHandler;

	@Override
	public void init() {
		captchaService = (CaptchaService) getServletContext().getAttribute(CAPTCHA_SERVICE);
		captchaHandler = (CaptchaHandler) getServletContext().getAttribute(CAPTCHA_HANDLER);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOG.debug("doPost() called");
		Captcha captcha = captchaService.generateCaptcha();
		LOG.info("Generate captcha: --> {}", captcha);
		captchaHandler.saveCaptcha(req, resp, captcha);

		String captchaImage = captchaService.drawCaptchaInBase64(captcha);
		req.setAttribute(CAPTCHA_IMAGE, captchaImage);
		LOG.info("Set the session attribute: " + CAPTCHA_IMAGE + " --> " + !captchaImage.isEmpty());

		LOG.debug("Go to forward address --> " + REGISTER_PAGE);
		getServletContext().getRequestDispatcher(REGISTER_PAGE).forward(req, resp);
	}
}
