package com.reflex.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.security.core.AuthenticationException;

@Component
public class CustomAuthenticationEntryPoint extends BasicAuthenticationEntryPoint{
	
	@Override
	public void commence (HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx) throws
	IOException, AuthenticationException{
		response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		PrintWriter writer = response.getWriter();
		writer.println("HTTP Status 401 - " + authEx.getMessage());
	}
	
	@Override
	public void afterPropertiesSet() {
		setRealmName("o7planning");
		super.afterPropertiesSet();
	}
}