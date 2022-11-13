package com.youth.controller;

import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.youth.dto.Board_FAQRequestDto;
import com.youth.service.Board_FAQService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class Board_FAQController {
	
	private final Board_FAQService board_FAQService;
	
	@GetMapping("/faq/list")
	public String getBoardListPage(Model model, String searchKeyword, Pageable pageable 
			, @RequestParam(required = false, defaultValue = "0") Integer page
			, @RequestParam(required = false, defaultValue = "5") Integer size) throws Exception {
		
		 if(searchKeyword == null) {
		
		try {
			model.addAttribute("resultMap", board_FAQService.findAll(page, size));
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		} else {
			model.addAttribute("resultMap", board_FAQService.findByTitleContaining(page, size, searchKeyword));
		}

		return "/faq/list";
	}
	
	@GetMapping("/faq/write")
	public String getBoardWritePage(Model model, Board_FAQRequestDto board_FAQRequestDto) {
		return "/faq/write";
		
	}
	
	@GetMapping("/faq/view")
	public String getBoardViewPage(Model model, Board_FAQRequestDto board_FAQRequestDto) throws Exception {
		
		try {
			if(board_FAQRequestDto.getId() != null) {
				model.addAttribute("info", board_FAQService.findById(board_FAQRequestDto.getId()));
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "/faq/view";
	}
	
	@PostMapping("/faq/write/action")
	public String boardWriteAction(Model model, Board_FAQRequestDto board_FAQRequestDto) throws Exception {
		
		try {
			Long result = board_FAQService.save(board_FAQRequestDto);
			
			if (result < 0) {
				throw new Exception("#Exception boardWriteAction!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "redirect:/faq/list";
	}
	
	@PostMapping("/faq/view/action")
	public String boardViewAction(Model model, Board_FAQRequestDto board_FAQRequestDto) throws Exception {
		
		try {
			int result = board_FAQService.updateBoard(board_FAQRequestDto);
			
			if (result < 1) {
				throw new Exception("#Exception boardViewAction!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "redirect:/faq/list";
	}
	
	@PostMapping("/faq/view/delete")
	public String boardViewDeleteAction(Model model, @RequestParam() Long id) throws Exception {
		
		try {
			board_FAQService.deleteById(id);
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "redirect:/faq/list";
	}
	
	@PostMapping("/faq/delete")
	public String boardDeleteAction(Model model, @RequestParam() Long[] deleteId) throws Exception {
		
		try {
			board_FAQService.deleteAll(deleteId);
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "redirect:/faq/list";
	}
}
