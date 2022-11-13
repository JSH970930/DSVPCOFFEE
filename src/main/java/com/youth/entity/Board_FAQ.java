package com.youth.entity;


import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="board_faq")
public class Board_FAQ extends NoticeFAQTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String title;
	private String content;
	private int readCnt;
	private String registerId;
	
	@Builder
	public Board_FAQ(Long id, String title, String content, int readCnt, String registerId) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.readCnt = readCnt;
		this.registerId = registerId;
	}
}
