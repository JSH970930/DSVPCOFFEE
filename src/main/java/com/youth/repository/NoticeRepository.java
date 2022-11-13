package com.youth.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.youth.dto.NoticeRequestDto;
import com.youth.entity.Notice;




public interface NoticeRepository extends JpaRepository<Notice, Long> {

	static final String UPDATE_NOTICE = "UPDATE Notice "
			+ "SET TITLE = :#{#noticeRequestDto.title}, "
			+ "CONTENT = :#{#noticeRequestDto.content}, "
			+ "UPDATE_TIME = NOW() "
			+ "WHERE ID = :#{#noticeRequestDto.id}";
	
	
	
	
	static final String UPDATE_NOTICE_READ_CNT_INC = "UPDATE notice "
			+ "SET read_cnt = read_cnt + 1 "
			+ "WHERE id = :id";
	
	static final String DELETE_NOTICE = "DELETE FROM Notice "
			+ "WHERE ID IN (:deleteList)";
	
	@Transactional
	@Modifying
	@Query(value = UPDATE_NOTICE, nativeQuery = true)

	public int updateNotice(@Param("noticeRequestDto") NoticeRequestDto noticeRequestDto);
	
	@Transactional
	@Modifying
	@Query(value = UPDATE_NOTICE_READ_CNT_INC, nativeQuery = true)
	public int updateNoticeReadCntInc(@Param("id") Long id);
	
	@Transactional
	@Modifying
	@Query(value = DELETE_NOTICE, nativeQuery = true)
	public int deleteNotice(@Param("deleteList")Long[] deleteList);
}
