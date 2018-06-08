package com.epam.preprod.web.exctractor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface RequestExtractor<T> {
	T extractFromRequest(HttpServletRequest req) throws IOException, ServletException;
}
