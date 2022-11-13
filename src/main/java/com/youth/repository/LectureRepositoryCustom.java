package com.youth.repository;

import com.youth.dto.LectureSearchDto;
import com.youth.dto.MainLectureDto;
import com.youth.entity.Lecture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LectureRepositoryCustom {

	Page<Lecture> getAdminLecturePage(LectureSearchDto lectureSearchDto, Pageable pageable);
	
	Page<MainLectureDto> getMainLecturePage(LectureSearchDto lectureSearchDto, Pageable pageable);
}
