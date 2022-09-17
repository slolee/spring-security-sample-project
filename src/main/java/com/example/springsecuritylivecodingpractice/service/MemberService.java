package com.example.springsecuritylivecodingpractice.service;

import org.springframework.security.crypto.password.PasswordEncoder;
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
	private final PasswordEncoder encoder;

	public MemberResponse retrieveMember(Long id) {
		return memberRepository.findById(id)
			.map(MemberResponse::of)
			.orElseThrow(() -> new RuntimeException("해당 회원정보를 찾을 수 없습니다."));
	}

	public MemberResponse register(MemberRegisterRequest req) {
		Member newMember = Member.builder()
			.email(req.getEmail())
			.password(encoder.encode(req.getPassword()))
			.nickname(req.getNickname())
			.build();

		Member createdMember = memberRepository.save(newMember);
		return MemberResponse.of(createdMember);
	}

	public Member validate(String email, String password) throws RuntimeException {
		return memberRepository.findByEmail(email)
			.filter(member -> encoder.matches(password, member.getPassword()))
			.orElseThrow(() -> new RuntimeException("해당 회원정보를 찾을 수 없습니다."));
	}

}
