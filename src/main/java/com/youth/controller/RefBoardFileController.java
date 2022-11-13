package com.youth.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.youth.dto.RefBoardFileRequestDto;
import com.youth.dto.RefBoardFileResponseDto;
import com.youth.service.RefBoardFileService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class RefBoardFileController {

	private final RefBoardFileService refBoardFileService;
	
	@GetMapping("/reffile/download")
	public void downloadRefFile(@RequestParam(defaultValue="1", required = false) Long refId, HttpServletResponse response) throws Exception {
		try {
			RefBoardFileResponseDto refFileInfo = refBoardFileService.findByRefId(refId);
			
			if(refFileInfo == null) throw new FileNotFoundException("Empty RefFileData.");
			
			File dFile = new File(refFileInfo.getFilePath(), refFileInfo.getSaveFileName());
			
			int fSize = (int)dFile.length();
			
			if(fSize > 0) {
				String encodedFilename = "attachment; filename*=" + "UTF-8" + "''" + URLEncoder.encode(refFileInfo.getOrigFileName(), "UTF-8");
				
				response.setContentType("application/octet-stream; charset=utf-8");
				
				response.setHeader("Content-Disposition", encodedFilename);
				
				response.setContentLengthLong(fSize);
				
				BufferedInputStream in = null;
				BufferedOutputStream out = null;
				
				in = new BufferedInputStream(new FileInputStream(dFile));
				
				out = new BufferedOutputStream(response.getOutputStream());
				
				try {
					byte[] buffer = new byte[4096];
					int bytesRead = 0;
					
					while((bytesRead = in .read(buffer)) != -1) {
						out.write(buffer, 0, bytesRead);
					}
					out.flush();
				} finally {
					in.close();
					out.close();
				}
			} else {
				throw new FileNotFoundException("Empty FileData.");
			}
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@PostMapping("/reffile/delete.ajax")
	public String updateRefDeleteYn(Model model, RefBoardFileRequestDto refBoardFileRequestDto) throws Exception {
		try {
		model.addAttribute("refResult", refBoardFileService.updateRefDeleteYn(refBoardFileRequestDto.getRefIdArr()));
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		
		return "jsonView";
	} 
}
