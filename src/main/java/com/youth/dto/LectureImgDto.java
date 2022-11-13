package com.youth.dto;

import com.youth.entity.LectureImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter

public class LectureImgDto {

	private Long id;
	private String imgName;
	private String oriImgName;
	private String imgUrl;
	private String repImgYn;
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static LectureImgDto of(LectureImg lectureImg) {
		return modelMapper.map(lectureImg, LectureImgDto.class);
	}
}
