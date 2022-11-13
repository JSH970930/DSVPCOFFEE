package com.youth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RefBoardFileRequestDto {

	private Long refId;
	private Long[] refIdArr;
	private String refFileId;
}
