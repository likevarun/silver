package com.rest.silver;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class RedirectFilter implements Filter {

	Logger logger = Logger.getLogger(RedirectFilter.class.getName());

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.info("-------------------Inside Filter-----------------------");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		// Incomming URL.
		String url = req.getRequestURL().toString();
		String idParam = req.getParameter("userId");
		String newUrl = null;
		logger.info("Filter Incoming URL = " + url);
		
		logger.info("idParam value:" + idParam);
		if (idParam == null) {
			Random rn = new Random();
			String token = Integer.toString(Math.abs(rn.nextInt(Integer.MAX_VALUE)),36);
			newUrl = url + "?" + "userId=" + token;
			logger.info("NewUrl values:" + newUrl);
			String ur = res.encodeRedirectURL(newUrl);
			logger.info("SendRedirect encoded:" + ur);
			res.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
			res.sendRedirect(ur);
			return;
		}

		logger.info("calling forward:" + url);
		chain.doFilter(request, response);
	}

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}
}
