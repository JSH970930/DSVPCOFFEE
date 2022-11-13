package com.youth.dto;

import com.youth.constant.LectureSellStatus;
import com.youth.entity.Lecture;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LectureFormDto {

	private Long id;
	
	@NotBlank(message = "강의명은 필수 입력 값입니다.")
	private String lectureNm;
	
	@NotNull(message = "가격은 필수 입력 값입니다.")
	private int price;
	
	@NotBlank(message = "강의 상세는 필수 입력 값입니다.")
	private String lectureDetail;
	
	private LectureSellStatus lectureSellStatus;
	
	private List<LectureImgDto> lectureImgDtoList = new ArrayList<>();
	
	private List<Long> lectureImgIds = new ArrayList<>();
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public Lecture createLecture() {
		return modelMapper.map(this, Lecture.class);
	}
	
	public static LectureFormDto of(Lecture lecture) {
		return modelMapper.map(lecture, LectureFormDto.class);
	}
}
