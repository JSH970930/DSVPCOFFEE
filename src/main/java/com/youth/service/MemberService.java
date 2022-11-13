package com.youth.service;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youth.DataNotFoundException;
import com.youth.dto.MemberFormDto;
import com.youth.entity.Member;
import com.youth.repository.MemberRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
	
	private final MemberRepository memberRepository;
	
	@Transactional
	public Member saveMember(Member member) {
		validateDuplicateMember(member);
		return memberRepository.save(member);
	}
	
	
	private void validateDuplicateMember(Member member){
		Member findMember = memberRepository.findByEmail(member.getEmail());
		if(findMember != null){
		throw new IllegalStateException("이미 가입된 회원입니다.");
		}

	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Member member = memberRepository.findByEmail(email);
		
		if(member == null) {
			throw new UsernameNotFoundException(email);
		}
		
		return User.builder()
				.username(member.getEmail())
				.password(member.getPassword())
				.roles(member.getRole().toString())
				.build();
	}

	public List<Member> findMembers() {
		return memberRepository.findAll();
	}
	
	@Transactional
	public MemberFormDto MemberRecord(String email) {
		Member member = memberRepository.findByEmail(email);
		MemberFormDto memberFormDto = member.toMemberFormDto(member);
		return memberFormDto;
	}
	
	@Transactional
	public Long updateMember(MemberFormDto memberFormDto) {
		Member member = memberFormDto.toEntity();
		memberRepository.save(member);
		
		return member.getId();
	}
	
	@Transactional
	public Long updateRole(MemberFormDto memberFormDto) {
		Member member = memberFormDto.toEntity();
		memberRepository.save(member);
		
		return member.getId();
	}
	
	@Transactional
	public Member findByEmail(String email) {
		Member member = memberRepository.findByEmail(email);
		return member;
	}
	
	public Member getUser(String email) {
        Optional<Member> member = this.memberRepository.findByemail(email);
        if (member.isPresent()) {
            return member.get();
        } else {
            throw new DataNotFoundException("member not found");
        }
    }
	
}
