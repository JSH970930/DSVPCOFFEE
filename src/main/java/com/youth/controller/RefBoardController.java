package com.youth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.youth.dto.RefBoardRequestDto;
import com.youth.service.RefBoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class RefBoardController {

	private final RefBoardService refBoardService;
	
	@GetMapping("/refboard/list")
	public String getRefBoardListPage(Model model, 
									  @RequestParam(required = false, defaultValue = "0") Integer page,
									  @RequestParam(required = false, defaultValue = "5") Integer size) throws Exception {
		try {
			model.addAttribute("refResultMap", refBoardService.findAll(page, size));
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		
		return "/refboard/list";
	}
	
	@GetMapping("/refboard/write")
	public String getRefBoardWritePage(Model model, RefBoardRequestDto refBoardRequestDto) {
		return "/refboard/write";
	}
	
	@GetMapping("/refboard/view")
	public String getRefBoardViewPage(Model model, RefBoardRequestDto refBoardRequestDto) throws Exception {
		try {
			if(refBoardRequestDto.getRefId() != null) {
				model.addAttribute("refResultMap", refBoardService.findByRefId(refBoardRequestDto.getRefId()));
			}
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		return "/refboard/view";
	}
	
	@PostMapping("/refboard/write/action")
	public String refBoardWriteAction(Model model, RefBoardRequestDto refBoardRequestDto, MultipartHttpServletRequest multiRequest) throws Exception {
		try {
			if(!refBoardService.save(refBoardRequestDto, multiRequest)) {
				throw new Exception("#Exception refBoardWriteAction!");
			}
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		
		return "redirect:/refboard/list";
	}
	
	@PostMapping("/refboard/view/action")
	public String refBoardViewAction(Model model, RefBoardRequestDto refBoardRequestDto, MultipartHttpServletRequest multiRequest) throws Exception {

		try {
			boolean refResult = refBoardService.updateRefBoard(refBoardRequestDto, multiRequest);
			
			if(!refResult) {
				throw new Exception("#Exception refBoardViewAction!");
			}
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		
		return "redirect:/refboard/list";
	}
	
	@PostMapping("/refboard/view/delete")
	public String refBoardViewDeleteAction(Model model, @RequestParam() Long refId) throws Exception {
		
		try {
			refBoardService.deleteByRefId(refId);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		return "redirect:/refboard/list";
	}
	
	@PostMapping("/refboard/delete")
	public String refBoardDeleteAction(Model model, @RequestParam() Long[] deleteRefIdList) throws Exception {
		try {
			refBoardService.deleteAll(deleteRefIdList);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		
		return "redirect:/refboard/list";
	}
}