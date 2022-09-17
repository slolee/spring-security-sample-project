package com.example.springsecuritylivecodingpractice.security.login;

import javax.transaction.NotSupportedException;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.example.springsecuritylivecodingpractice.entity.Member;
import com.example.springsecuritylivecodingpractice.security.exception.CustomAuthenticationException;
import com.example.springsecuritylivecodingpractice.service.MemberService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginAuthenticationProvider implements AuthenticationProvider {

	private final MemberService memberService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		LoginAuthenticationToken beforeToken = (LoginAuthenticationToken) authentication;

		try {
			Member member = memberService.validate(beforeToken.getEmail(), beforeToken.getPassword());
			return LoginAuthenticationToken.afterOf(member.getId());
		} catch (RuntimeException ex) {
			throw new CustomAuthenticationException(ex);
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return LoginAuthenticationToken.class.isAssignableFrom(aClass);
	}

}
