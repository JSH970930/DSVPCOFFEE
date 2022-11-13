package com.youth.service;

import java.util.HashMap;

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youth.dto.NoticeRequestDto;
import com.youth.dto.NoticeResponseDto;
import com.youth.entity.Notice;
import com.youth.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NoticeService {

private final NoticeRepository noticeRepository;
	
	@Transactional
	public Long save(NoticeRequestDto noticeSaveDto) {
		return noticeRepository.save(noticeSaveDto.toEntity()).getId();
	}
	
	
	@Transactional(readOnly = true)
	public HashMap<String, Object> findAll(Integer page, Integer size) {
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		Page<Notice> list = noticeRepository.findAll(PageRequest.of(page, size , Sort.by(Sort.Direction.DESC, "registerTime")));
		resultMap.put("list", list.stream().map(NoticeResponseDto::new).collect(Collectors.toList()));
		resultMap.put("paging", list.getPageable());
		resultMap.put("totalCnt", list.getTotalElements());
		resultMap.put("totalPage", list.getTotalPages());
		
		return resultMap;
	}
	
	public NoticeResponseDto findById(Long id) {
		noticeRepository.updateNoticeReadCntInc(id);
		return new NoticeResponseDto(noticeRepository.findById(id).get());
	}
	
	public int updateNotice(NoticeRequestDto noticeRequestDto) {
		return noticeRepository.updateNotice(noticeRequestDto);
	}
	
	     
	 public int updateNoticeReadCntInc(Long id) {        
		 return noticeRepository.updateNoticeReadCntInc(id);  
		 }
	 
	
	
	public void deleteById(Long id) {
		noticeRepository.deleteById(id);
	}
	
	public void deleteAll(Long[] deleteId) {
		noticeRepository.deleteNotice(deleteId);
	}

}
