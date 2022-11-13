package com.youth.dto;

import com.youth.entity.Notice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class NoticeRequestDto {

	private Long id;
	private String title;
	private String content;
	private String registerId;

	public Notice toEntity() {
		return Notice
				.builder()
				.title(title)
				.content(content)
				.registerId(registerId)
				.build();
	}

	public String toString() {
		return "NoticeRequestDto [id=" + id + ", title=" + title + ", content=" + content + ", registerId=" + registerId
				+ "]";
	}

}
