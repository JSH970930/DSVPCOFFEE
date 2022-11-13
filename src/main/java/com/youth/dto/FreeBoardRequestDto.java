package com.youth.dto;

import com.youth.entity.FreeBoard;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class FreeBoardRequestDto {
	private Long fboardno;
	private String title;
	private String content;
	private String writer;
	
	public FreeBoard toEntity() {
		return FreeBoard.builder()
				.title(title)
				.content(content)
				.writer(writer)
				.build();
	}

}