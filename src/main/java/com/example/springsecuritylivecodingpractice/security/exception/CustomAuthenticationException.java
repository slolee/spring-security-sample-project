package com.example.springsecuritylivecodingpractice.security.exception;

import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationException extends AuthenticationException {

	public CustomAuthenticationException(Exception ex) {
		super(ex.getMessage(), ex);
	}

}
