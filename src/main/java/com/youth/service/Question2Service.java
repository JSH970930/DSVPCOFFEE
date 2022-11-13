package com.youth.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.youth.DataNotFoundException;
import com.youth.entity.Member;
import com.youth.entity.Question2;
import com.youth.repository.Question2Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class Question2Service {

    private final Question2Repository question2Repository;

  public Page<Question2> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 5, Sort.by(sorts));
        return this.question2Repository.findAll(pageable);
    }
   
    public Question2 getQuestion2(Integer id) {
        Optional<Question2> question2 = this.question2Repository.findById(id);
        if (question2.isPresent()) {
            return question2.get();
        } else {
            throw new DataNotFoundException("question2 not found");
        }
    }

    public Question2 create(String subject, String content, Member user) {
        Question2 question2 = new Question2();
        question2.setSubject(subject);
        question2.setContent(content);
        question2.setCreateDate(LocalDateTime.now());
        question2.setAuthor(user);
        this.question2Repository.save(question2);
        return question2;
    }

    public void modify(Question2 question2, String subject, String content) {
        question2.setSubject(subject);
        question2.setContent(content);
        question2.setModifyDate(LocalDateTime.now());
        this.question2Repository.save(question2);
    }

    public void delete(Question2 question2) {
        this.question2Repository.delete(question2);
    }
}