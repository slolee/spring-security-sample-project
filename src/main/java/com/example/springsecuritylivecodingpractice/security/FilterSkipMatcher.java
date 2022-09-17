package com.example.springsecuritylivecodingpractice.security;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class FilterSkipMatcher implements RequestMatcher {

	private final OrRequestMatcher orRequestMatcher;

	public FilterSkipMatcher(List<String> pathToSkip) {
		// 예외 주소로 등록
		this.orRequestMatcher = new OrRequestMatcher(
			pathToSkip.stream()
				.map(AntPathRequestMatcher::new)
				.collect(Collectors.toList())
		);
	}

	@Override
	public boolean matches(HttpServletRequest req) {
		return !orRequestMatcher.matches(req);
	}

}
