package com.youth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.youth.entity.RefBoardFile;

public interface RefBoardFileRepository extends JpaRepository<RefBoardFile, Long> {

	static final String SELECT_REF_FILE_ID = "SELECT REF_ID FROM ref_board_file " +
										 "WHERE REF_BOARD_ID = :refBoardId AND REF_DELETE_YN != 'Y'";
	
	static final String UPDATE_REF_DELETE_YN = "UPDATE ref_board_file " + 
										   "SET REF_DELETE_YN = 'Y' " +
										   "WHERE REF_ID IN (:deleteRefIdList)";
	
	static final String DELETE_REF_BOARD_FILE_YN = "UPDATE ref_board_file " + 
												   "SET REF_DELETE_YN = 'Y' " + 
												   "WHERE REF_BOARD_ID IN(:refBoardIdList)";
	
	@Query(value = SELECT_REF_FILE_ID, nativeQuery = true)
	public List<Long> findByRefBoardId(@Param("refBoardId") Long refBoardId);
	
	@Transactional
	@Modifying
	@Query(value = UPDATE_REF_DELETE_YN, nativeQuery = true)
	public int updateRefDeleteYn(@Param("deleteRefIdList") Long[] deleteRefIdList);
	
	@Transactional
	@Modifying
	@Query(value = DELETE_REF_BOARD_FILE_YN, nativeQuery = true)
	public int deleteRefBoardFileYn(@Param("refBoardIdList") Long[] refBoardIdList);
}
