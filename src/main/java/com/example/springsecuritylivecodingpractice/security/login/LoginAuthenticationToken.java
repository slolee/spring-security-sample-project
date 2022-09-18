package com.example.springsecuritylivecodingpractice.security.login;

import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.example.springsecuritylivecodingpractice.endpoint.request.LoginRequest;
import com.example.springsecuritylivecodingpractice.entity.Member;

public class LoginAuthenticationToken extends UsernamePasswordAuthenticationToken {

	private LoginAuthenticationToken(Object principal, Object credentials) {
		super(principal, credentials);
	}

	private LoginAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
	}

	public static LoginAuthenticationToken beforeOf(LoginRequest req) {
		return new LoginAuthenticationToken(req.getEmail(), req.getPassword());
	}

	public static LoginAuthenticationToken afterOf(Member member) {
		return new LoginAuthenticationToken(member.getId(), "", List.of(member.getRole()));
	}

	public String getEmail() {
		if (this.isAuthenticated()) {
			throw new RuntimeException();
		}
		return (String) this.getPrincipal();
	}

	public String getPassword() {
		if (this.isAuthenticated()) {
			throw new RuntimeException();
		}
		return (String) this.getCredentials();
	}

	public Long getId() {
		if (!this.isAuthenticated()) {
			throw new RuntimeException();
		}
		return (Long) this.getPrincipal();
	}

}
