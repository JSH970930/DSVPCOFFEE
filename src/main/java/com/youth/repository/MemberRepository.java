package com.youth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.youth.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	
	boolean existsByName(String name);
	boolean existsByEmail(String email);	
	Member findByEmail(String email);
	Member findByName(String name);
	
	  Optional<Member> findByemail(String email);
}