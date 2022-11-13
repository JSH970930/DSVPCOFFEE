package com.youth.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderDto {

	@NotNull(message = "강의 아이디는 필수 입력 값입니다.")
	private Long LectureId;
	
	@Min(value = 1, message = "최소 주문 수량은 1개입니다.")
	@Max(value = 1, message = "최대 주문 수량은 1개입니다.")
	private int count;
}
