package com.youth.dto;

import com.youth.entity.OrderLecture;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderLectureDto {

	public OrderLectureDto(OrderLecture orderLecture, String imgUrl) {
		this.lectureNm = orderLecture.getLecture().getLectureNm();
		this.count = orderLecture.getCount();
		this.orderPrice = orderLecture.getOrderPrice();
		this.imgUrl = imgUrl;
	}
	
	private String lectureNm;
	private int count;
	private int orderPrice;
	private String imgUrl;
}
