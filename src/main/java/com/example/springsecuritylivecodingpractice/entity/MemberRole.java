package com.example.springsecuritylivecodingpractice.entity;

import org.springframework.security.core.GrantedAuthority;

public enum MemberRole implements GrantedAuthority {

	UNCERTIFIED, CERTIFIED;

	@Override
	public String getAuthority() {
		return this.name();
	}

}
