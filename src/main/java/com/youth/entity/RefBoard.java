package com.youth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class RefBoard extends RefBaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ref_id", nullable = false)
	private Long refId;
	
	@Column(nullable = false)
	private String refTitle;
	
	@Column(nullable = false)
	private String refContent;
	
	@Column(nullable = false)
	private int refReadCnt;
	
	@Column(nullable = false)
	private String refRegisterId;
	
	@Builder
	public RefBoard(Long refId, String refTitle, String refContent, int refReadCnt, String refRegisterId) {
		this.refId = refId;
		this.refTitle = refTitle;
		this.refContent = refContent;
		this.refReadCnt = refReadCnt;
		this.refRegisterId = refRegisterId;
	}
}
