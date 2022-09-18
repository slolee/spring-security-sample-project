package com.example.springsecuritylivecodingpractice.security.jwt;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.example.springsecuritylivecodingpractice.entity.Member;
import com.example.springsecuritylivecodingpractice.helper.JwtHelper;
import com.example.springsecuritylivecodingpractice.repository.MemberRepository;
import com.example.springsecuritylivecodingpractice.security.exception.CustomAuthenticationException;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

	private final MemberRepository memberRepository;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		JwtAuthenticationToken beforeToken = (JwtAuthenticationToken) authentication;
		String accessToken = beforeToken.getAccessToken();

		try {
			if (!JwtHelper.validateJwt(accessToken)) {
				throw new CustomAuthenticationException("Invalid AccessToken!");
			}
			long memberId = Long.parseLong(JwtHelper.getClaim(accessToken, Claims::getSubject));
			Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomAuthenticationException("Not Found This Member!"));
			return JwtAuthenticationToken.afterOf(member);
		} catch (RuntimeException ex) {
			throw new CustomAuthenticationException("Invalid AccessToken!");
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return JwtAuthenticationToken.class.isAssignableFrom(aClass);
	}

}
