package com.youth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.youth.dto.Question2Form;
import com.youth.entity.Member;
import com.youth.entity.Question2;
import com.youth.service.MemberService;
import com.youth.service.Question2Service;

import javax.validation.Valid;
import java.security.Principal;

@RequestMapping("/question2")
@RequiredArgsConstructor
@Controller
public class Question2Controller {

    private final Question2Service question2Service;
    private final MemberService memberService;

    @RequestMapping("/list")
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<Question2> paging = this.question2Service.getList(page);
        model.addAttribute("paging", paging);
        return "/qnaboard2/question2_list";
    }

    @RequestMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Question2 question2 = this.question2Service.getQuestion2(id);
        model.addAttribute("question2", question2);
        return "/qnaboard2/question2_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String question2Create() {
        return "/qnaboard2/question2_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String question2Create(@Valid Question2Form question2Form,
                                 BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "/qnaboard2/question2_form";
        }
        Member member = this.memberService.getUser(principal.getName());
        this.question2Service.create(question2Form.getSubject(), question2Form.getContent(), member);
        return "redirect:/question2/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String question2Modify(Question2Form question2Form, @PathVariable("id") Integer id, Principal principal) {
        Question2 question2 = this.question2Service.getQuestion2(id);
        if(!question2.getAuthor().getEmail().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        question2Form.setSubject(question2.getSubject());
        question2Form.setContent(question2.getContent());
        return "/qnaboard2/question2_form";
    }

    
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String question2Modify(@Valid Question2Form question2Form, BindingResult bindingResult, 
            Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "/qnaboard2/question2_form";
        }
        Question2 question2 = this.question2Service.getQuestion2(id);
        if (!question2.getAuthor().getEmail().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.question2Service.modify(question2, question2Form.getSubject(), question2Form.getContent());
        return String.format("redirect:/question2/detail/%s", id);
    }
    
    
    
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/delete/{id}")
    public String question2Delete(Principal principal, @PathVariable("id") Integer id) {
        Question2 question2 = this.question2Service.getQuestion2(id);
        if (!question2.getAuthor().getEmail().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.question2Service.delete(question2);
        return "redirect:/question2/list";
    }
    
    
}