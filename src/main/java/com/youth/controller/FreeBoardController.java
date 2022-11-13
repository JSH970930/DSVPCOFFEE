package com.youth.controller;

import java.security.Principal;


import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.youth.dto.FreeBoardRequestDto;

import com.youth.entity.Member;
import com.youth.repository.FreeBoardRepository;
import com.youth.service.FreeBoardService;
import com.youth.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FreeBoardController {
	
	private final FreeBoardService fbService;

	
	@GetMapping("/freeboard/list")
	public String getFreeBoardListPage(Model model, @RequestParam(required = false, defaultValue = "0")
										Integer page, @RequestParam(required = false, defaultValue = "5")
										Integer size) throws Exception{
		
		try {
			model.addAttribute("resultMap", fbService.findAll(page, size));
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		return "/freeboard/list";
	}
	
	@GetMapping("/freeboard/write")
	public String getFreeBoardWritePage(Model model, FreeBoardRequestDto fbRequestDto) throws Exception{
		
		return "/freeboard/write";
	}
	
	@GetMapping("/freeboard/view")
	public String getFreeBoardViewPage(Model model, FreeBoardRequestDto fbRequestDto) throws Exception {
		try {
			if (fbRequestDto.getFboardno() != null) {
				model.addAttribute("info", fbService.findById(fbRequestDto.getFboardno()));
			}
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		return "/freeboard/view";
	}
	
	@PostMapping("/freeboard/write/action")
//	@PreAuthorize("hasRole('USER')")
	public String FreeBoardWriteAction(Model model, FreeBoardRequestDto fbRequestDto) throws Exception{
		
		try {
			Long result = fbService.save(fbRequestDto);
			
			if(result < 0) {
				throw new Exception("#Exception freeboardWriteAction!");
			}
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		};
		
		
		return "redirect:/freeboard/list";
	}
	
	@PostMapping("/freeboard/view/action")
	public String freeboardViewAction(Model model, FreeBoardRequestDto fbRequestDto) throws Exception{
		try {
			int result = fbService.updateFreeBoard(fbRequestDto);
			
			if (result < 1) {
				throw new Exception("#Exception freeboardViewAction!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "redirect:/freeboard/list";
	}
	
	@PostMapping("/freeboard/view/delete")
	public String freeboardViewDeleteAction(Model model, @RequestParam() Long fboardno) throws Exception{
		try {
			fbService.deleteById(fboardno);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "redirect:/freeboard/list";
	}
	
	@PostMapping("/freeboard/delete")
	public String freeboardDeleteAction(Model model, @RequestParam() Long[] deleteFboardno) throws Exception{
		try {
			fbService.deleteAll(deleteFboardno);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		return "redirect:/freeboard/list";
	}
	

}