package com.youth.dto;



import java.time.LocalDateTime;

import com.youth.entity.Board_FAQ;

import lombok.Getter;

@Getter
public class Board_FAQResponseDto {
	private Long id;
	private String title;
	private String content;
	private int readCnt;
	private String registerId;
	private LocalDateTime registerTime;
	
	public Board_FAQResponseDto(Board_FAQ entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.content = entity.getContent();
		this.readCnt = entity.getReadCnt();
		this.registerId = entity.getRegisterId();
		this.registerTime = entity.getRegisterTime();
	}
	
	@Override
	public String toString() {
		return "Board_FAQResponseDto [id=" + id + ", title=" + title + ", content=" + content + ", readCnt=" + readCnt
						+ ", registerId=" + registerId + ", registerTime=" + registerTime + "]";
	}
}
