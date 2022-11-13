package com.youth.service;

import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youth.dto.Board_FAQRequestDto;
import com.youth.dto.Board_FAQResponseDto;
import com.youth.entity.Board_FAQ;
import com.youth.repository.Board_FAQRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Board_FAQService {
	
private final Board_FAQRepository board_FAQRepository;
	
	@Transactional
	public Long save(Board_FAQRequestDto boardSaveDto) {
		return board_FAQRepository.save(boardSaveDto.toEntity()).getId();
	}
	
	@Transactional(readOnly = true)
	public HashMap<String, Object> findAll(Integer page, Integer size) {
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		Page<Board_FAQ> list = board_FAQRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "registerTime")));
		resultMap.put("list", list.stream().map(Board_FAQResponseDto::new).collect(Collectors.toList()));
		resultMap.put("paging", list.getPageable());
		resultMap.put("totalCnt", list.getTotalElements());
		resultMap.put("totalPage", list.getTotalPages());
		
		return resultMap;
	}
	
	@Transactional
	   public HashMap<String, Object> findByTitleContaining(Integer page, Integer size, String searchKeyword) {

	      HashMap<String, Object> resultMap = new HashMap<String, Object>();
	      Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
	      Page<Board_FAQ> list = board_FAQRepository.findByTitleContaining(pageable, searchKeyword);
	   
	      resultMap.put("list", list.stream().map(Board_FAQResponseDto::new).collect(Collectors.toList()));
	      resultMap.put("paging", list.getPageable());
	      resultMap.put("totalCnt", list.getTotalElements());
	      resultMap.put("totalPage", list.getTotalPages());
	      
	      return resultMap;
	   }
	
	public Board_FAQResponseDto findById(Long id) {
		board_FAQRepository.updateBoardReadCntInc(id);
		return new Board_FAQResponseDto(board_FAQRepository.findById(id).get());
	}
	
	public int updateBoard(Board_FAQRequestDto board_FAQRequestDto) {
		return board_FAQRepository.updateBoard(board_FAQRequestDto);
	}
	

	public void deleteById(Long id) {
		board_FAQRepository.deleteById(id);
	}
	
	public void deleteAll(Long[] deleteId) {
		board_FAQRepository.deleteBoard(deleteId);
	}


}
