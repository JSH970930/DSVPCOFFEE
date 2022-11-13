package com.youth.service;

import com.youth.dto.LectureFormDto;
import com.youth.dto.LectureImgDto;
import com.youth.dto.LectureSearchDto;
import com.youth.dto.MainLectureDto;
import com.youth.entity.Lecture;
import com.youth.entity.LectureImg;
import com.youth.repository.LectureImgRepository;
import com.youth.repository.LectureRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class LectureService {
	
	private final LectureRepository lectureRepository;
	private final LectureImgService lectureImgService;
	private final LectureImgRepository lectureImgRepository;
	
	public Long saveLecture(LectureFormDto lectureFormDto, List<MultipartFile> lectureImgFileList) throws Exception {
		// 강의 등록
		Lecture lecture = lectureFormDto.createLecture();
		lectureRepository.save(lecture);
		
		// 이미지 등록
		for(int i = 0; i < lectureImgFileList.size(); i++) {
			LectureImg lectureImg = new LectureImg();
			lectureImg.setLecture(lecture);
			
			if(i == 0)
				lectureImg.setRepImgYn("Y");
			else
				lectureImg.setRepImgYn("N");
			lectureImgService.saveLectureImg(lectureImg, lectureImgFileList.get(i));
		}
		
		return lecture.getId();
	}
	
	@Transactional(readOnly = true)
	public LectureFormDto getLectureDtl(Long lectureId) {
		List<LectureImg> lectureImgList = lectureImgRepository.findByLectureIdOrderByIdAsc(lectureId);
		List<LectureImgDto> lectureImgDtoList = new ArrayList<>();
		for (LectureImg lectureImg : lectureImgList) {
			LectureImgDto lectureImgDto = LectureImgDto.of(lectureImg);
			lectureImgDtoList.add(lectureImgDto);
		}
		
		Lecture lecture = lectureRepository.findById(lectureId)
				.orElseThrow(EntityNotFoundException::new);
		LectureFormDto lectureFormDto = LectureFormDto.of(lecture);
		lectureFormDto.setLectureImgDtoList(lectureImgDtoList);
		return lectureFormDto;
	}
	
	public Long updateLecture(LectureFormDto lectureFormDto, List<MultipartFile> lectureImgFileList) throws Exception {
		Lecture lecture = lectureRepository.findById(lectureFormDto.getId())
				.orElseThrow(EntityNotFoundException::new);
		lecture.updateLecture(lectureFormDto);
		List<Long> lectureImgIds = lectureFormDto.getLectureImgIds();
		
		for (int i = 0; i < lectureImgFileList.size(); i++) {
			lectureImgService.updateLectureImg(lectureImgIds.get(i), lectureImgFileList.get(i));
		}
		return lecture.getId();
	}
	
	@Transactional(readOnly = true)
	public Page<Lecture> getAdminLecturePage(LectureSearchDto lectureSearchDto, Pageable pageable) {
		return lectureRepository.getAdminLecturePage(lectureSearchDto, pageable);
	}
	
	@Transactional(readOnly = true)
	public Page<MainLectureDto> getMainLecturePage(LectureSearchDto lectureSearchDto, Pageable pageable) {
		return lectureRepository.getMainLecturePage(lectureSearchDto, pageable);
	}
	
}
