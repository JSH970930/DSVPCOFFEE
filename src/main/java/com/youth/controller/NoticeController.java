package com.youth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.youth.dto.NoticeRequestDto;
import com.youth.dto.NoticeResponseDto;
import com.youth.service.NoticeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class NoticeController {
	
	private final NoticeService noticeService;
	
	
	@GetMapping("/notice/list")
	public String getNoticeListPage(Model model
			, @RequestParam(required = false, defaultValue = "0") Integer page
			, @RequestParam(required = false, defaultValue = "5") Integer size) throws Exception {
		
		try {
			model.addAttribute("resultMap", noticeService.findAll(page, size));
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "/notice/list";
	}
	
	@GetMapping("/notice/write")
	public String getNoticeWritePage(Model model, NoticeRequestDto noticeRequestDto) {
		return "/notice/write";
		
	}
	
	@GetMapping("/notice/view")
	public String getNoticeViewPage(Model model, NoticeRequestDto noticeRequestDto) throws Exception {
		
		try {
			if(noticeRequestDto.getId() != null) {
				model.addAttribute("info", noticeService.findById(noticeRequestDto.getId()));
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "/notice/view";
	}
	
	@PostMapping("/notice/write/action")
	public String noticeWriteAction(Model model, NoticeRequestDto noticeRequestDto) throws Exception {
		
		try {
			Long result = noticeService.save(noticeRequestDto);
			
			if (result < 0) {
				throw new Exception("#Exception noticeWriteAction!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "redirect:/notice/list";
	}
	
	@PostMapping("/notice/view/action")
	public String noticeViewAction(Model model, NoticeRequestDto noticeRequestDto) throws Exception {
		
		try {
			int result = noticeService.updateNotice(noticeRequestDto);
			
			if (result < 1) {
				throw new Exception("#Exception noticeViewAction!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "redirect:/notice/list";
	}
	
	@PostMapping("/notice/view/delete")
	public String noticeViewDeleteAction(Model model, @RequestParam() Long id) throws Exception {
		
		try {
			noticeService.deleteById(id);
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "redirect:/notice/list";
	}
	
	@PostMapping("/notice/delete")
	public String noticeDeleteAction(Model model, @RequestParam() Long[] deleteId) throws Exception {
		
		try {
			noticeService.deleteAll(deleteId);
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "redirect:/notice/list";
	}


	
	
	
}
	


