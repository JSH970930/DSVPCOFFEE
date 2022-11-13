package com.youth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.youth.entity.Lecture;

public interface LectureRepository extends JpaRepository<Lecture, Long>, QuerydslPredicateExecutor<Lecture>, LectureRepositoryCustom {
	
	List<Lecture> findByLectureNm(String lectureNm);
	List<Lecture> findByLectureNmOrLectureDetail(String lectureNm, String lectureDetail);
}
