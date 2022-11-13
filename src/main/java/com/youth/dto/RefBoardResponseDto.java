package com.youth.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.youth.entity.RefBoard;

import lombok.Getter;

@Getter
public class RefBoardResponseDto {

	private Long refId;
	private String refTitle;
	private String refContent;
	private int refReadCnt;
	private String refRegisterId;
	private LocalDateTime refRegisterTime;
	
	public RefBoardResponseDto(RefBoard entity) {
		this.refId = entity.getRefId();
		this.refTitle = entity.getRefTitle();
		this.refContent = entity.getRefContent();
		this.refReadCnt = entity.getRefReadCnt();
		this.refRegisterId = entity.getRefRegisterId();
		this.refRegisterTime = entity.getRefRegisterTime();
	}
	
	@Override
	public String toString() {
		return "RefBoardIdListDto [refId=" + refId + ", refTitle=" + refTitle + ", refContent" + refContent +
				", refReadCnt=" + refReadCnt + ", refRegisterId=" + refRegisterId + ", refRegisterTime=" + refRegisterTime + "]";
	}
	
	public String getRefRegisterTime() {
		return  toStringDateTime(this.refRegisterTime);
	}

	public static String toStringDateTime(LocalDateTime localDateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return Optional.ofNullable(localDateTime)
				.map(formatter::format)
				.orElse("");
	}
	
}
