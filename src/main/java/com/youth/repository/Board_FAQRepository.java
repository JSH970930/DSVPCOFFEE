package com.youth.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.youth.dto.Board_FAQRequestDto;
import com.youth.entity.Board_FAQ;



@Repository
public interface Board_FAQRepository extends JpaRepository<Board_FAQ, Long> {
	
	Page<Board_FAQ> findByTitleContaining(Pageable pageable, String searchKeyword);
	
	static final String UPDATE_BOARD = "UPDATE board_FAQ "
			+ "SET TITLE = :#{#board_FAQRequestDto.title}, "
			+ "CONTENT = :#{#board_FAQRequestDto.content}, "
			+ "REGISTER_ID = :#{#board_FAQRequestDto.registerId}, "
			+ "UPDATE_TIME = SYSDATE() "
			+ "WHERE ID = :#{#board_FAQRequestDto.id}";
			
	
	static final String UPDATE_BOARD_READ_CNT_INC = "UPDATE board_FAQ "
			+ "SET READ_CNT = READ_CNT + 1 "
			+ "WHERE ID = :id";
	
	static final String DELETE_BOARD = "DELETE FROM board_FAQ "
			+ "WHERE ID IN (:deleteList)";
	
	@Transactional
	@Modifying
	@Query(value = UPDATE_BOARD, nativeQuery = true)
	public int updateBoard(@Param("board_FAQRequestDto") Board_FAQRequestDto board_FAQRequestDto);
	
	@Transactional
	@Modifying
	@Query(value = UPDATE_BOARD_READ_CNT_INC, nativeQuery = true)
	public int updateBoardReadCntInc(@Param("id") Long id);
	
	@Transactional
	@Modifying
	@Query(value = DELETE_BOARD, nativeQuery = true)
	public int deleteBoard(@Param("deleteList") Long[] deleteList);
	
	
	
}
