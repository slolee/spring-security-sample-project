package com.example.springsecuritylivecodingpractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springsecuritylivecodingpractice.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
