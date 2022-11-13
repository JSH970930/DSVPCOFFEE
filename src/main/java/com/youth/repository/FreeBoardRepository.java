package com.youth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.youth.dto.FreeBoardRequestDto;
import com.youth.entity.FreeBoard;

public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long>{
	
	String UPDATE_FREEBOARD = "UPDATE free_board " +
			"SET TITLE = :#{#FreeBoardRequestDto.title}, " +
			"CONTENT = :#{#FreeBoardRequestDto.content}, " +
			"update_date = NOW() " +
			"WHERE fboardno = :#{#FreeBoardRequestDto.fboardno}";
	
	@Transactional
	@Modifying
	@Query(value = UPDATE_FREEBOARD, nativeQuery = true)
	public int updateFreeBoard(@Param("FreeBoardRequestDto") FreeBoardRequestDto fbRequestDto);
	
	static final String UPDATE_FREEBOARD_READ_CNT_INC = "UPDATE free_board "
										+ "SET read_cnt = read_cnt + 1 "
										+ "WHERE fboardno = :fboardno";
	
	static final String DELETE_FREEBOARD = "DELETE FROM free_board "
									+ "WHERE fboardno IN (:deleteList)";
	
	@Transactional
	@Modifying
	@Query(value = UPDATE_FREEBOARD_READ_CNT_INC, nativeQuery = true)
	public int updateFreeBoardReadCntInc(@Param("fboardno")Long fboardno);
	
	@Transactional
	@Modifying
	@Query(value = DELETE_FREEBOARD, nativeQuery = true)
	public int deleteFreeBoard(@Param("deleteList") Long[] deleteList);


}
