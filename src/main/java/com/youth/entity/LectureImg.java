package com.youth.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name="lecture_img")
@Getter
@Setter
@SequenceGenerator(
		name = "LECTURE_IMG_SEQUENCE_GENERATOR",
		sequenceName = "LECTURE_IMG_SEQ",
		initialValue = 1,
		allocationSize = 1)
public class LectureImg extends BaseEntity {

	@Id
	@Column(name="lecture_img_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LECTURE_IMG_SEQUENCE_GENERATOR")
	private Long id;
	
	private String imgName;
	private String oriImgName;
	private String imgUrl;
	private String repImgYn;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lecture_id")
	private Lecture lecture;
	
	public void updateLectureImg(String oriImgName, String imgName, String imgUrl) {
		this.oriImgName = oriImgName;
		this.imgName = imgName;
		this.imgUrl = imgUrl;
	}
}
