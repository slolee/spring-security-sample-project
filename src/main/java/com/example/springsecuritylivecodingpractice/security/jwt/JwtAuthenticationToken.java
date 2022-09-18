package com.example.springsecuritylivecodingpractice.security.jwt;

import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.example.springsecuritylivecodingpractice.entity.Member;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

	private JwtAuthenticationToken(Object principal, Object credentials) {
		super(principal, credentials);
	}
	private JwtAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
	}

	public static JwtAuthenticationToken beforeOf(String accessToken) {
		return new JwtAuthenticationToken(accessToken, "");
	}

	public static JwtAuthenticationToken afterOf(Member member) {
		return new JwtAuthenticationToken(member.getId(), "", List.of(member.getRole()));
	}

	public String getAccessToken() {
		if (this.isAuthenticated()) {
			throw new RuntimeException();
		}
		return (String) this.getPrincipal();
	}

}
