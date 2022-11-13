package com.youth.service;

import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youth.dto.FreeBoardRequestDto;
import com.youth.dto.FreeBoardResponseDto;
import com.youth.entity.FreeBoard;
import com.youth.entity.Member;
import com.youth.repository.FreeBoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FreeBoardService {
	
	private final FreeBoardRepository fbRepository;
	
	@Transactional
	public Long save(FreeBoardRequestDto fbSaveDto) {			
		
		return fbRepository.save(fbSaveDto.toEntity()).getFboardno();
	}
	

	/*
	 * 트랜잭션에 readOnly=true 옵션을 주면 스프링 프레임워크가 하이버네이트 세션 플러시 모드를 MANUAL로 설정한다. 이렇게하면
	 * 강제로 플러시를 호출하지 않는 한 플러시가 일어나지 않는다. 따라서 트랜잭션을 커밋하더라도 영속성 컨텍스특 플러시 되지 않아서 엔티티의
	 * 등록, 수정, 삭제가 동작하지 않고, 또한 읽기 전용으로, 영속성 컨텍스트는 변경 감지를 위한 스냅샷을 보관하지 않으므로 성능이 향상된다.
	 */
	
	@Transactional(readOnly = true)
	public HashMap<String, Object> findAll(Integer page, Integer size){
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		Page<FreeBoard> list = fbRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createDate")));
		
		resultMap.put("list", list.stream().map(FreeBoardResponseDto::new).collect(Collectors.toList()));
		resultMap.put("paging", list.getPageable());
		resultMap.put("totalCnt", list.getTotalElements());
		resultMap.put("totalPage", list.getTotalPages());
		
		return resultMap;
	}
	
	
	public FreeBoardResponseDto findById(Long fboardno) {
		fbRepository.updateFreeBoardReadCntInc(fboardno);
		return new FreeBoardResponseDto(fbRepository.findById(fboardno).get());
	}
	
	public int updateFreeBoard(FreeBoardRequestDto fbRequestDto) {
		return fbRepository.updateFreeBoard(fbRequestDto);
	}
	
	public void deleteById(Long fboardno) {
		fbRepository.deleteById(fboardno);
	}
	
	public void deleteAll(Long[] deleteFboardno) {
		fbRepository.deleteFreeBoard(deleteFboardno);
	}

}