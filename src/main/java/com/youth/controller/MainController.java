package com.youth.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.youth.dto.LectureSearchDto;
import com.youth.dto.MainLectureDto;
import com.youth.service.LectureService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	private final LectureService lectureService;
	
	@GetMapping(value = "/")
	public String main() {
		return "mainPage";
	}
	
	@GetMapping(value = "/admin/homeUpdate") // 앞에 '/' 절대 경로 써주면 안 됨 
	public String homePage() {
		return "/adminPage/homeUpdate";
	}
	
	@GetMapping(value = "introPage")
	public String introPage() {
		return "/nav/introPage";
	}
	
	@GetMapping(value = "collaboPage")
	public String collaboPage() {
		return "/nav/collaboPage";
	}
	
	@GetMapping(value = "careerTestPage")
	public String careerTestPage() {
		return "/nav/careerTestPage";
	}
	
	@GetMapping(value = "consultingPage")
	public String consltingPage() {
		return "/nav/consultingPage";
	}
	
	@GetMapping(value = "jobPer")
	public String jobPerPage() {
		return "/nav/jobPerPage";
	}
	
	
	@GetMapping(value = "lectures")
	public String lectures(LectureSearchDto lectureSearchDto, Optional<Integer> page, Model model) {
		
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
		Page<MainLectureDto> lectures = lectureService.getMainLecturePage(lectureSearchDto, pageable);
		
		model.addAttribute("lectures", lectures);
		model.addAttribute("lectureSearchDto", lectureSearchDto);
		model.addAttribute("maxPage", 5);
		model.addAttribute("lectureNumber", lectures.getNumber());
		model.addAttribute("lectureTotalPages", lectures.getTotalPages());

		
		return "lecture/mainLecture";
	}
}