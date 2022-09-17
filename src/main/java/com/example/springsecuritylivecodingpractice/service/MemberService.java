package com.example.springsecuritylivecodingpractice.service;

import org.springframework.stereotype.Service;

import com.example.springsecuritylivecodingpractice.endpoint.request.MemberRegisterRequest;
import com.example.springsecuritylivecodingpractice.endpoint.response.MemberResponse;
import com.example.springsecuritylivecodingpractice.entity.Member;
import com.example.springsecuritylivecodingpractice.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public MemberResponse retrieveMember(Long id) {
		return memberRepository.findById(id)
			.map(MemberResponse::of)
			.orElseThrow(() -> new RuntimeException("해당 회원정보를 찾을 수 없습니다."));
	}

	public MemberResponse register(MemberRegisterRequest req) {
		Member newMember = Member.builder()
			.email(req.getEmail())
			.password(req.getPassword()) // TODO : 패스워드 암호화
			.nickname(req.getNickname())
			.build();

		Member createdMember = memberRepository.save(newMember);
		return MemberResponse.of(createdMember);
	}

}
