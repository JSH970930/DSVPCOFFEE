package com.youth.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.youth.entity.Question2;



@Repository
public interface Question2Repository extends JpaRepository<Question2, Integer> {
    Question2 findBySubject(String subject);
    Question2 findBySubjectAndContent(String subject, String content);
    List<Question2> findBySubjectLike(String subject);
    Page<Question2> findAll(Pageable pageable);
}