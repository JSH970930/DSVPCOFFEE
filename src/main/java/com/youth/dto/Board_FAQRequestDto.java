package com.youth.dto;


import com.youth.entity.Board_FAQ;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Board_FAQRequestDto {
	private Long id;
	private String title;
	private String content;
	private String registerId;
	
	public Board_FAQ toEntity() {
		return Board_FAQ.builder()
			.title(title)
			.content(content)
			.registerId(registerId)
			.build();
	}

	@Override
	public String toString() {
		return "Board_FAQRequestDto [id=" + id + ", title=" + title + ", content=" + content + ", registerId=" + registerId
				+ "]";
	}

	

}
