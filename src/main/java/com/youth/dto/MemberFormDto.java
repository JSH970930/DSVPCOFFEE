package com.youth.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.youth.constant.Role;
import com.youth.entity.Member;
import com.youth.entity.Member.MemberBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberFormDto {
	
	private Long id;
	
	@Autowired
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@NotBlank(message = "닉네임은 필수 입력 값입니다.")
	private String name;
	
	@NotBlank(message = "이메일은 필수 입력 값입니다.")
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")
	private String email;
	
	@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대/소문자, 숫자, 특수문자를 사용하세요.")	
	private String password;
	
	@NotBlank(message = "간단한 소개를 써주세요.")
	private String oneline;
	private Role role;
	
	
	public Member toEntity() {
		System.out.println(this.password);
		return Member.builder()
				.id(id)
				.name(name)
				.email(email)
				.password(password)
				.oneline(oneline)
				.role(role)
				.build();
	}
}
