package com.example.springsecuritylivecodingpractice.endpoint.request;

import lombok.Data;

@Data
public class MemberRegisterRequest {

	private String email;
	private String password;
	private String nickname;

}
