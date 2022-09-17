package com.example.springsecuritylivecodingpractice.security.exception.handler;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.example.springsecuritylivecodingpractice.security.exception.CustomAuthenticationException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException failed) {
		CustomAuthenticationException ex = (CustomAuthenticationException) failed;

		res.setStatus(HttpStatus.UNAUTHORIZED.value());
		res.setContentType(MediaType.APPLICATION_JSON_VALUE);

		try (OutputStream os = res.getOutputStream()) {
			new ObjectMapper().writeValue(os, ex.getMessage());
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
