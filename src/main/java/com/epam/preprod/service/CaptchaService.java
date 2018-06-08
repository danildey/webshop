package com.epam.preprod.service;


import com.epam.preprod.web.bean.Captcha;

import java.io.IOException;

public interface CaptchaService {
	Captcha generateCaptcha();

	String drawCaptchaInBase64(Captcha captcha) throws IOException;
}
