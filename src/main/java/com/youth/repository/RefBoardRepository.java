package com.youth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.youth.dto.RefBoardRequestDto;
import com.youth.entity.RefBoard;

public interface RefBoardRepository extends JpaRepository<RefBoard, Long> {

	static final String UPDATE_REF_BOARD = "UPDATE Ref_Board " + 
							  "SET REF_TITLE = :#{#refBoardRequestDto.refTitle}, " +
							  "REF_CONTENT = :#{#refBoardRequestDto.refContent}, " +
							  "REF_REGISTER_ID = :#{#refBoardRequestDto.refRegisterId}, " +
							  "REF_UPDATE_TIME = NOW() " +
							  "WHERE REF_ID = :#{#refBoardRequestDto.refId}";

	static final String UPDATE_REF_BOARD_READ_CNT_INC = "UPDATE Ref_Board "
														+ "SET REF_READ_CNT = REF_READ_CNT + 1 "
														+ "WHERE REF_ID = :refId";
	
	static final String DELETE_REF_BOARD = "DELETE FROM Ref_Board "
											+ "WHERE REF_ID IN(:deleteRefId)";
	
	
	@Transactional
	@Modifying
	@Query(value = UPDATE_REF_BOARD, nativeQuery = true)
	public int updateRefBoard(@Param("refBoardRequestDto") RefBoardRequestDto refBoardRequestDto);
	
	@Transactional
	@Modifying
	@Query(value = UPDATE_REF_BOARD_READ_CNT_INC, nativeQuery = true)
	public int updateRefBoardReadCntInc(@Param("refId") Long refId);
	
	@Transactional
	@Modifying
	@Query(value = DELETE_REF_BOARD, nativeQuery = true)
	public int deleteRefBoard(@Param("deleteRefId") Long[] deleteRefIdList);
}
