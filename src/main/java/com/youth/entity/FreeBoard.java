package com.youth.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
//@Table(name = "FreeBoard")
public class FreeBoard extends FreeBoardTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long fboardno;
	private String title;
	private String content;
	private int readCnt;
	private String writer;
	
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member email;
	
	

	@Builder
	public FreeBoard(Long fboardno, String title, String content, int readCnt, String writer) {
		this.fboardno = fboardno;
		this.title = title;
		this.content = content;
		this.readCnt = readCnt;
		this.writer = writer;
	}
	

}
