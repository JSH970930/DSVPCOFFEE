package com.youth.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.youth.dto.Answer2Form;
import com.youth.entity.Answer2;
import com.youth.entity.Member;
import com.youth.entity.Question2;
import com.youth.service.Answer2Service;
import com.youth.service.MemberService;
import com.youth.service.Question2Service;

import javax.validation.Valid;
import java.security.Principal;

@RequestMapping("/answer2")
@RequiredArgsConstructor
@Controller
public class Answer2Controller {

    private final Question2Service question2Service;
    private final Answer2Service answer2Service;
    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer2(Model model, @PathVariable("id") Integer id,
                               @Valid Answer2Form answer2Form, BindingResult bindingResult, Principal principal) {
        Question2 question2 = this.question2Service.getQuestion2(id);
        Member member = this.memberService.getUser(principal.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("question2", question2);
            return "/qnaboard2/question2_detail";
        }
        this.answer2Service.create(question2, answer2Form.getContent(), member);
        return String.format("redirect:/question2/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String answer2Modify(Answer2Form answer2Form, @PathVariable("id") Integer id, Principal principal) {
        Answer2 answer2 = this.answer2Service.getAnswer2(id);
        if (!answer2.getAuthor().getEmail().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        answer2Form.setContent(answer2.getContent());
        return "/qnaboard2/answer2_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String answer2Modify(@Valid Answer2Form answer2Form, BindingResult bindingResult,
                               @PathVariable("id") Integer id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "/qnaboard2/answer2_form";
        }
        Answer2 answer2 = this.answer2Service.getAnswer2(id);
        if (!answer2.getAuthor().getEmail().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.answer2Service.modify(answer2, answer2Form.getContent());
        return String.format("redirect:/question2/detail/%s", answer2.getQuestion2().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String answer2Delete(Principal principal, @PathVariable("id") Integer id) {
        Answer2 answer2 = this.answer2Service.getAnswer2(id);
        if (!answer2.getAuthor().getEmail().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.answer2Service.delete(answer2);
        return String.format("redirect:/question2/detail/%s", answer2.getQuestion2().getId());
    }
}