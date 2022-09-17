package com.example.springsecuritylivecodingpractice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	private String email;
	private String password;
	private String nickname;

	@Enumerated(EnumType.STRING)
	private MemberType type = MemberType.GENERAL;

	@Builder
	public Member(String email, String password, String nickname, MemberType type) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.type = type;
	}

}
