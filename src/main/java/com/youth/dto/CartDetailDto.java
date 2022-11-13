package com.youth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDetailDto {

	private Long cartLectureId;
	
	private String lectureNm;
	
	private int price;
	
	private int count;
	
	private String imgUrl;
	
	public CartDetailDto(Long cartLectureId, String lectureNm, int price, int count, String imgUrl) {
		this.cartLectureId = cartLectureId;
		this.lectureNm = lectureNm;
		this.price = price;
		this.count = count;
		this.imgUrl = imgUrl;
	}
}
