package com.youth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.youth.constant.LectureSellStatus;
import com.youth.dto.LectureFormDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="lecture")
@Getter
@Setter
@ToString
@SequenceGenerator(
		name = "LECTURE_SEQUENCE_GENERATOR",
		sequenceName = "LECTURE_SEQ",
		initialValue = 1,
		allocationSize = 1)
public class Lecture extends BaseEntity {
	
	@Id
	@Column(name = "lecture_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LECTURE_SEQUENCE_GENERATOR")
	private Long id;
	
	@Column(nullable = false, length = 50)
	private String lectureNm;	// 제목
	
	@Column(name = "price", nullable = false)
	private int price;
	
	@Lob
	@Column(name = "lectureDetail", nullable = false)
	private String lectureDetail;
	
	@Enumerated(EnumType.STRING)
	private LectureSellStatus lectureSellStatus;
	
	public void updateLecture(LectureFormDto lectureFormDto) {
		this.lectureNm = lectureFormDto.getLectureNm();
		this.price = lectureFormDto.getPrice();
		this.lectureDetail = lectureFormDto.getLectureDetail();
		this.lectureSellStatus = lectureFormDto.getLectureSellStatus();
	}
	
	
}
