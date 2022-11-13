package com.youth.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
//@Table(name="Ref_Board_File")
public class RefBoardFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long refId;
	
	@Column(nullable=false)
	private Long refBoardId;
	
	private String origFileName;
	private String saveFileName;
	private int fileSize;
	private String fileExt;
	private String filePath;
	private String refDeleteYn;
	
	@CreatedDate
	private LocalDateTime registerTime;
	
	@Builder
	public RefBoardFile(Long refId, Long refBoardId, String origFileName, String saveFileName
						, int fileSize, String fileExt, String filePath, String refDeleteYn, LocalDateTime registerTime) {
		this.refId = refId;
		this.refBoardId = refBoardId;
		this.origFileName = origFileName;
		this.saveFileName = saveFileName;
		this.fileSize = fileSize;
		this.fileExt = fileExt;
		this.filePath = filePath;
		this.refDeleteYn = refDeleteYn;
		this.registerTime = registerTime;
	}
}
