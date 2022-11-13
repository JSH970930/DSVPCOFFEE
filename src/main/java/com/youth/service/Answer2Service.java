package com.youth.service;



import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.youth.DataNotFoundException;
import com.youth.entity.Answer2;
import com.youth.entity.Member;
import com.youth.entity.Question2;
import com.youth.repository.Answer2Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class Answer2Service {

    private final Answer2Repository answer2Repository;


    public void create(Question2 question2, String content, Member author) {
        Answer2 answer2 = new Answer2();
        answer2.setContent(content);
        answer2.setCreateDate(LocalDateTime.now());
        answer2.setQuestion2(question2);
        answer2.setAuthor(author);
        this.answer2Repository.save(answer2);
    }

    public Answer2 getAnswer2(Integer id) {
        Optional<Answer2> answer2 = this.answer2Repository.findById(id);
        if (answer2.isPresent()) {
            return answer2.get();
        } else {
            throw new DataNotFoundException("answer2 not found");
        }
    }

    public void modify(Answer2 answer2, String content) {
        answer2.setContent(content);
        answer2.setModifyDate(LocalDateTime.now());
        this.answer2Repository.save(answer2);
    }

    public void delete(Answer2 answer2) {
        this.answer2Repository.delete(answer2);
    }
}