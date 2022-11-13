package com.youth.dto;

import com.youth.entity.RefBoardFile;

import lombok.Getter;

@Getter
public class RefBoardFileResponseDto {

	private String origFileName;
	private String saveFileName;
	private String filePath;
	
	public RefBoardFileResponseDto(RefBoardFile entity) {
		this.origFileName = entity.getOrigFileName();
		this.saveFileName = entity.getSaveFileName();
		this.filePath = entity.getFilePath();
	}
	
	@Override
	public String toString() {
		return "FileMstResponseDto [origFileName=" + origFileName + ", saveFileName=" 
									+ saveFileName + ", filePath=" + filePath + "]";
	}
}
