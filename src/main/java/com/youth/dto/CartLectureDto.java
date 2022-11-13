package com.youth.dto;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CartLectureDto {

	@NotNull(message = "강의 아이디는 필수 입력 값입니다.")
	private Long lectureId;
	
	@Min(value = 1, message = "최소 1개 이상 담아주세요.")
	private int count;
}
