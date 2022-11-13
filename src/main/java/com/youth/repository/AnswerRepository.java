package com.youth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.youth.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {

}