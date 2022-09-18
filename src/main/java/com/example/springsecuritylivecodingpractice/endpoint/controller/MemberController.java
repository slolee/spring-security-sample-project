package com.example.springsecuritylivecodingpractice.endpoint.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecuritylivecodingpractice.endpoint.request.MemberRegisterRequest;
import com.example.springsecuritylivecodingpractice.endpoint.response.MemberResponse;
import com.example.springsecuritylivecodingpractice.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/api/member/{id}")
	public ResponseEntity<MemberResponse> retrieveMember(@PathVariable Long id) {
		SecurityContextHolder.getContext().getAuthentication().getAuthorities().forEach(System.out::println);
		return ResponseEntity.ok(memberService.retrieveMember(id));
	}

	@PreAuthorize("hasAuthority('CERTIFIED')")
	@GetMapping("/api/me")
	public ResponseEntity<MemberResponse> retrieveMyInfo() {
		Long id = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ResponseEntity.ok(memberService.retrieveMember(id));
	}

	@PostMapping("/register")
	public ResponseEntity<MemberResponse> register(@RequestBody MemberRegisterRequest req) {
		return ResponseEntity.ok(memberService.register(req));
	}

}
