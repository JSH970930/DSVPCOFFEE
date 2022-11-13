package com.youth.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Notice extends NoticeFAQTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String content;
	private int readCnt;
	private String registerId;
	
	@Builder
	public Notice(Long id, String title, String content, int readCnt, String registerId) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.readCnt = readCnt;
		this.registerId = registerId;
	}
}
