package com.youth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.youth.service.FreeBoardService;

import lombok.RequiredArgsConstructor;

@RestController("/DSVP/*")
@RequiredArgsConstructor
public class DsvpController {
	private final FreeBoardService fbService;
	
	@RequestMapping("/{scrinId}")
	public String getFreeBoardListPage(Model model,@PathVariable String scrinId, @RequestParam(required = false, defaultValue = "0")
										Integer page, @RequestParam(required = false, defaultValue = "5")
										Integer size) throws Exception{
		
		try {
			model.addAttribute("resultMap", fbService.findByTitleContaining(page, size, scrinId));
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		System.out.println(scrinId+"힘을내요 슈퍼파월");
		return "/DSVP"+scrinId;
	}

}
