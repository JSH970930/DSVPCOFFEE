package com.youth.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.youth.dto.LectureFormDto;
import com.youth.dto.LectureSearchDto;
import com.youth.entity.Lecture;
import com.youth.service.LectureService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LectureController {
	
	private final LectureService lectureService;

	@GetMapping(value = "/admin/lecture/new")
	public String lectureForm(Model model) {
		model.addAttribute("lectureFormDto", new LectureFormDto());
		return "lecture/lectureForm";
	}
	
	@PostMapping(value = "/admin/lecture/new")
	public String lectureNew(@Valid LectureFormDto lectureFormDto, BindingResult bindingResult, Model model, @RequestParam("lectureImgFile") List<MultipartFile> lectureImgFileList) {
		
		if(bindingResult.hasErrors()) {
			return "lecture/lectureForm";
		}
		
		if(lectureImgFileList.get(0).isEmpty() && lectureFormDto.getId() == null) {
			model.addAttribute("errorMessage", "첫 번째 강의 이미지는 필수 입력 값입니다.");
			return "lecture/lectureForm";
		}
		
		try {
			lectureService.saveLecture(lectureFormDto, lectureImgFileList);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "강의 등록 중 에러가 발생하였습니다.");
			return "lecture/lectureForm";
		}
		
		return "redirect:/";
	}
	
	@GetMapping(value="/admin/lecture/{lectureId}")
	public String lectureDtl(@PathVariable("lectureId") Long lectureId, Model model) {
		
		try {
			LectureFormDto lectureFormDto = lectureService.getLectureDtl(lectureId);
			model.addAttribute("lectureFormDto", lectureFormDto);
		} catch (EntityNotFoundException e) {
			model.addAttribute("errorMessage", "존재하지 않는 강의입니다.");
			model.addAttribute("lectureFromDto", new LectureFormDto());
			return "lecture/lectureForm";
		}
		return "lecture/lectureForm";
	}
	
	@PostMapping(value = "/admin/lecture/{lectureId}")
	public String lectureUpdate(@Valid LectureFormDto lectureFormDto, BindingResult bindingResult,
			@RequestParam("lectureImgFile") List<MultipartFile> lectureImgFileList, Model model) {
		if(bindingResult.hasErrors()) {
			return "lecture/lectureForm";
		}
		
		if(lectureImgFileList.get(0).isEmpty() && lectureFormDto.getId() == null) {
			model.addAttribute("errorMessage", "첫 번째 강의 이미지는 필수 입력 값입니다.");
			return "lecture/lectureForm";
		}
		
		try {
			lectureService.updateLecture(lectureFormDto, lectureImgFileList);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "강의 수정 중 에러가 발생하였습니다.");
			return "lecture/lectureForm";
		}
		return "redirect:/";
	}
	
	@GetMapping(value = {"/admin/lectures", "/admin/lectures/{page}"})
	public String lectureManage(LectureSearchDto lectureSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {
	
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
		
		Page<Lecture> lectures = lectureService.getAdminLecturePage(lectureSearchDto, pageable);
		
	
		model.addAttribute("lectures", lectures);
		model.addAttribute("lectureSearchDto", lectureSearchDto);
		model.addAttribute("maxPage", 5);
		model.addAttribute("lectureNumber", lectures.getNumber());
		model.addAttribute("lectureTotalPages", lectures.getTotalPages());
		
		return "lecture/lectureMng";
	}
	
	@GetMapping(value = "/lecture/{lectureId}")
	public String lectureDtl(Model model, @PathVariable("lectureId") Long lectureId) {
		LectureFormDto lectureFormDto = lectureService.getLectureDtl(lectureId);
		model.addAttribute("lecture", lectureFormDto);
		return "lecture/lectureDtl";
	}
	
	
	
	
}
