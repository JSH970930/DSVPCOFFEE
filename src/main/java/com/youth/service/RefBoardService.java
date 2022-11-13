package com.youth.service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.youth.dto.RefBoardRequestDto;
import com.youth.dto.RefBoardResponseDto;
import com.youth.entity.RefBoard;
import com.youth.repository.RefBoardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RefBoardService {

	private final RefBoardRepository refBoardRepository;
	private final RefBoardFileService refBoardFileService;
	
	@Transactional
	public boolean save(RefBoardRequestDto refBoardRequestDto, MultipartHttpServletRequest multiRequest) throws Exception {
		
		RefBoard refResult = refBoardRepository.save(refBoardRequestDto.toEntity());
		
		boolean refResultFlag = false;
		
		if (refResult != null) {
			refBoardFileService.uploadFile(multiRequest, refResult.getRefId());
			refResultFlag = true;
		}
		
		return refResultFlag;
	}
	
	@Transactional(readOnly = true)
	public HashMap<String, Object> findAll(Integer page, Integer size) throws Exception {
		
		HashMap<String, Object> refResultMap = new HashMap<String, Object>();
		
		Page<RefBoard> refList = refBoardRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "refRegisterTime")));
		
		refResultMap.put("refList", refList.stream().map(RefBoardResponseDto::new).collect(Collectors.toList()));
		refResultMap.put("refPaging", refList.getPageable());
		refResultMap.put("refTotalCnt", refList.getTotalElements());
		refResultMap.put("refTotalPage", refList.getTotalPages());
		
		return refResultMap;
	}
	
	public HashMap<String, Object> findByRefId(Long refId) throws Exception {
		
		HashMap<String, Object> refResultMap = new HashMap<String, Object>(); 
		
		refBoardRepository.updateRefBoardReadCntInc(refId);
		
		RefBoardResponseDto refInfo = new RefBoardResponseDto(refBoardRepository.findById(refId).get());
		
		refResultMap.put("refInfo", refInfo);
		refResultMap.put("fileList", refBoardFileService.findByRefBoardId(refInfo.getRefId()));
		
		return refResultMap;
	}
	
	public boolean updateRefBoard(RefBoardRequestDto refBoardRequestDto, MultipartHttpServletRequest multiRequest) throws Exception {
		
		int refResult = refBoardRepository.updateRefBoard(refBoardRequestDto);
		
		boolean refResultFlag = false;
		
		if (refResult > 0) {
			refBoardFileService.uploadFile(multiRequest, refBoardRequestDto.getRefId());
			refResultFlag = true;
		}
		
		return refResultFlag;
	}
	
	public void deleteByRefId(Long refId) throws Exception {
		Long[] refIdArr = {refId};
		refBoardFileService.deleteRefBoardFileYn(refIdArr);
		refBoardRepository.deleteById(refId);
	}
	
	public void deleteAll(Long[] deleteRefIdList) throws Exception {
		refBoardFileService.deleteRefBoardFileYn(deleteRefIdList);
		refBoardRepository.deleteRefBoard(deleteRefIdList);
	}
}
