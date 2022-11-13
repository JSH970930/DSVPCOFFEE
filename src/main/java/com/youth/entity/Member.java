package com.youth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.youth.constant.Role;
import com.youth.dto.MemberFormDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="member")
@Getter
@Setter
@ToString
@NoArgsConstructor
@SequenceGenerator(
		name = "MEMBER_SEQUENCE_GENERATOR",
		sequenceName = "MEMBER_SEQ",
		initialValue = 1,
		allocationSize = 1)

public class Member extends BaseEntity {
	@Id
	@Column(name="member_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQUENCE_GENERATOR")
	private Long id;
	
	private String name;
	
	@Column(unique = true)
	private String email;
	
	private String password;
	
	private String oneline;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		Member member = new Member();
		member.setName(memberFormDto.getName());
		member.setEmail(memberFormDto.getEmail());
		String password = passwordEncoder.encode(memberFormDto.getPassword());
		member.setPassword(password);
		member.setOneline(memberFormDto.getOneline());
//		member.setRole(Role.ADMIN); // 어드민으로 만들 때
//		member.setRole(Role.SUB_AD); // 서브어드민으로 만들 때
		member.setRole(Role.USER); // 유저로 만들 때 and 유저로만 만든 뒤 어드민으로 서브어드민 설정

		return member;				
	}
	
	public MemberFormDto toMemberFormDto(Member member) {
		MemberFormDto memberFormDto = new MemberFormDto();
		memberFormDto.setId(member.id);
		memberFormDto.setName(member.name);
		memberFormDto.setEmail(member.email);
		memberFormDto.setOneline(member.oneline);
		memberFormDto.setRole(member.role);
		
		return memberFormDto;
	}
	
	@Builder
	public Member(Long id, String name, String email, String password, String oneline, Role role) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.oneline = oneline;
		this.role = role;
	}
}
