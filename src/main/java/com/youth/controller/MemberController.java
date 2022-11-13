package com.youth.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.youth.constant.Role;
import com.youth.dto.MemberFormDto;
import com.youth.entity.Member;
import com.youth.service.MemberService;

import lombok.RequiredArgsConstructor;

//@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	
	private final Logger LOGGER = LoggerFactory.getLogger(MemberController.class.getName());
	
	@GetMapping(value = "/members/new")
	public String memberForm(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "member/memberForm";
	} 
	
	@PostMapping(value = "/members/new")
	public String newMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
		
		if (bindingResult.hasErrors()) {
			return "member/memberForm";
		}
		
		try {
			Member member = Member.createMember(memberFormDto, passwordEncoder);
			memberService.saveMember(member);
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "member/memberForm";
		}
		
		return "redirect:/";
	}	
	
	@GetMapping(value = "/members/login")
	public String loginMember() {
		return "/member/memberLoginForm";
	}
	
	@GetMapping(value = "/members/login/error")
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg", "이메일 또는 비밀번호를 확인해주세요.");
		return "/member/memberLoginForm";
	}	
	
	@GetMapping(value ="/admin/list")
	public String list(Model model) {
		List<Member> members = memberService.findMembers();
		model.addAttribute("members", members);
		return "/member/membersList";
	}
	
	@GetMapping(value = "/members/updateMember")
	public String updateMemberForm(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String name = ((UserDetails) principal).getUsername();
		
		MemberFormDto memberFormDto = memberService.MemberRecord(name);
		model.addAttribute("memberFormDto", memberFormDto);
		return "member/updateMemberForm";
	}
	
	@PostMapping(value = "/members/updateMember")
	public String updateMember(@Valid MemberFormDto updateMemberFormDto, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "member/updateMemberForm";
		}
		try {
			memberService.updateMember(updateMemberFormDto);
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "member/updateMemberForm";
		}
		return "member/updateMemberForm";
	}
	
	@GetMapping(value = "/admin/updateRole")
	public String updateRoleForm(@RequestParam(value = "email", required=false) String email, Model model) {
		
		LOGGER.info(email);
		
		Member member = memberService.findByEmail(email);
		Role role = member.getRole();
		if(role.toString().equals("ADMIN")){
			List<Member> members = memberService.findMembers();
			model.addAttribute("members", members);
			return "/member/membersList";
		}
		
		LOGGER.info(member.getPassword());
		
		model.addAttribute("member", member);
		return "member/updateRole";
	}
	
	@PostMapping(value = "/admin/updateRole")
	public String updateRole(MemberFormDto updateMemberFormDto, Model model) {
		memberService.updateRole(updateMemberFormDto);
		
		LOGGER.info(updateMemberFormDto.getPassword());
		
		List<Member> members = memberService.findMembers();
		model.addAttribute("members", members);
		return "/member/membersList";
	}
}