package com.example.springsecuritylivecodingpractice.security.exception.handler;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException ex) {
		res.setStatus(HttpStatus.FORBIDDEN.value());
		res.setContentType(MediaType.APPLICATION_JSON_VALUE);

		try (OutputStream os = res.getOutputStream()) {
			new ObjectMapper().writeValue(os, ex.getMessage());
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
