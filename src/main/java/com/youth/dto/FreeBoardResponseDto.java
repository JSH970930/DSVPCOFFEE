package com.youth.dto;

import java.time.LocalDateTime;

import com.youth.entity.FreeBoard;

import lombok.Getter;

@Getter
public class FreeBoardResponseDto {
	private Long fboardno;
	private String title;
	private String content;
	private int readCnt;
	private String writer;
	private LocalDateTime createDate;
	
	
	public FreeBoardResponseDto(FreeBoard entity) {
		this.fboardno = entity.getFboardno();
		this.title = entity.getTitle();
		this.content = entity.getContent();
		this.readCnt = entity.getReadCnt();
		this.writer = entity.getWriter();
		this.createDate = entity.getCreateDate();
		
	}
	
	@Override
	public String toString() {
		return "FreeBoardResponseDto [fboardno=" + fboardno + ", title=" + title +
				", content=" + content + ", readCnt=" + readCnt +
				", writer=" + writer + ", createDate=" + createDate + "]";
	}

}