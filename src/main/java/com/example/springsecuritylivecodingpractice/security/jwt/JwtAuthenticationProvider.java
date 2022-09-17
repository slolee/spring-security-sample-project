package com.example.springsecuritylivecodingpractice.security.jwt;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.example.springsecuritylivecodingpractice.helper.JwtHelper;
import com.example.springsecuritylivecodingpractice.security.exception.CustomAuthenticationException;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		JwtAuthenticationToken beforeToken = (JwtAuthenticationToken) authentication;
		String accessToken = beforeToken.getAccessToken();

		try {
			if (!JwtHelper.validateJwt(accessToken)) {
				throw new CustomAuthenticationException("Invalid AccessToken!");
			}
			return JwtAuthenticationToken.afterOf(Long.valueOf(JwtHelper.getClaim(accessToken, Claims::getSubject)));
		} catch (RuntimeException ex) {
			throw new CustomAuthenticationException("Invalid AccessToken!");
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return JwtAuthenticationToken.class.isAssignableFrom(aClass);
	}

}
