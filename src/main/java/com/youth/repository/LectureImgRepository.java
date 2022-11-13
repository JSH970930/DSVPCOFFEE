package com.youth.repository;

import com.youth.entity.LectureImg;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureImgRepository extends JpaRepository<LectureImg, Long> {

	List<LectureImg> findByLectureIdOrderByIdAsc(Long lectureId);
	
	LectureImg findByLectureIdAndRepImgYn(Long lectureId, String repImgYn);
}
