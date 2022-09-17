package com.example.springsecuritylivecodingpractice.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public static final String HEADER_PREFIX = "Bearer ";

	public JwtAuthenticationFilter(RequestMatcher matcher) {
		super(matcher);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
		String accessToken = req.getHeader("Authorization").substring(HEADER_PREFIX.length());
		JwtAuthenticationToken beforeToken = JwtAuthenticationToken.beforeOf(accessToken);
		return super.getAuthenticationManager().authenticate(beforeToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws ServletException, IOException {
		JwtAuthenticationToken afterToken = (JwtAuthenticationToken) auth;

		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(afterToken);
		SecurityContextHolder.setContext(context);

		chain.doFilter(req, res);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse res, AuthenticationException failed) throws ServletException, IOException {
		SecurityContextHolder.clearContext();
		super.getFailureHandler().onAuthenticationFailure(req, res, failed);
	}
}
