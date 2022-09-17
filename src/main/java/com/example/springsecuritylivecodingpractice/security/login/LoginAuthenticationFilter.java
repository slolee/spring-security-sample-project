package com.example.springsecuritylivecodingpractice.security.login;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.example.springsecuritylivecodingpractice.endpoint.request.LoginRequest;
import com.example.springsecuritylivecodingpractice.helper.JwtHelper;
import com.example.springsecuritylivecodingpractice.helper.JwtType;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;

public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public LoginAuthenticationFilter(String defaultUrl) {
		super(defaultUrl);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException, IOException {
		LoginRequest loginRequest = new ObjectMapper().readValue(req.getReader(), LoginRequest.class);
		LoginAuthenticationToken beforeToken = LoginAuthenticationToken.beforeOf(loginRequest);
		return super.getAuthenticationManager().authenticate(beforeToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication authResult) throws IOException {
		LoginAuthenticationToken afterToken = (LoginAuthenticationToken) authResult;

		String accessToken = JwtHelper.generateJwt(afterToken.getId(), JwtType.ACCESS_TOKEN);
		String refreshToken = JwtHelper.generateJwt(afterToken.getId(), JwtType.REFRESH_TOKEN);

		res.addCookie(
			createCookie("refresh_token", refreshToken, JwtHelper.getClaim(refreshToken, Claims::getExpiration))
		);
		res.setStatus(HttpStatus.OK.value());
		res.setContentType(MediaType.APPLICATION_JSON_VALUE);
		res.getWriter().println(accessToken);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse res, AuthenticationException failed) throws IOException {
		System.out.println("실패!");
	}

	private Cookie createCookie(String name, String value, Date expireDate) {
		Cookie refreshTokenCookie = new Cookie(name, value);
		refreshTokenCookie.setHttpOnly(true);
		int maxAge = (int) ((expireDate.getTime() - new Date(System.currentTimeMillis()).getTime()) / 1000);
		refreshTokenCookie.setMaxAge(maxAge);

		return refreshTokenCookie;
	}

}
