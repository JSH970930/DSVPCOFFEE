package com.youth.dto;

import com.youth.constant.LectureSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LectureSearchDto {

	private String searchDateType;
	
	private LectureSellStatus searchSellStatus;
	
	private String searchBy;
	
	private String searchQuery = "";
}
