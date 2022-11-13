package com.youth.service;


import java.io.File;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.youth.dto.RefBoardFileResponseDto;
import com.youth.entity.RefBoardFile;
import com.youth.repository.RefBoardFileRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RefBoardFileService {

private final RefBoardFileRepository refBoardFileRepository;
	
	public RefBoardFileResponseDto findByRefId(Long refId) throws Exception {
		return new RefBoardFileResponseDto(refBoardFileRepository.findById(refId).get());
	}
	
	public List<Long> findByRefBoardId(Long refBoardId) throws Exception {
		return refBoardFileRepository.findByRefBoardId(refBoardId);
	}
	
	public boolean uploadFile(MultipartHttpServletRequest multiRequest, Long refBoardId) throws Exception {
		
		if (refBoardId == null) throw new NullPointerException("Empty REF_BOARD_ID.");
		
		// 파라미터 이름을 키로 파라미터에 해당하는 파일 정보를 값으로 하는 Map을 가져온다.
		Map<String, MultipartFile> files = multiRequest.getFileMap();
		
		// files.entrySet()의 요소를 읽어온다.
		Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
		
		MultipartFile mFile;
		
		String savaFilePath = "", randomFileName = "";
		
		Calendar cal = Calendar.getInstance();

		List<Long> refResultList = new ArrayList<Long>();
		
		while (itr.hasNext()) {
		
			Entry<String, MultipartFile> entry = itr.next();
	
			mFile = entry.getValue();
			
			int fileSize = (int) mFile.getSize();
			
			if (fileSize > 0) {
				
				String filePath = "C:\\dev_tools\\eclipse\\workspace\\uploadFiles\\";
				
				// 파일 업로드 경로 + 현재 년월(월별관리)
				filePath = filePath + File.separator + String.valueOf(cal.get(Calendar.YEAR)) + File.separator + String.valueOf(cal.get(Calendar.MONTH) + 1);
				randomFileName = "FILE_" + RandomStringUtils.random(8, 0, 0, false, true, null, new SecureRandom());
				
				String realFileName = mFile.getOriginalFilename();
				String fileExt = realFileName.substring(realFileName.lastIndexOf(".") + 1);
				String saveFileName = randomFileName + "." + fileExt;
				String saveFilePath = filePath + File.separator + saveFileName;
				
				File filePyhFolder = new File(filePath);
				
				if (!filePyhFolder.exists()) {
					
					if (!filePyhFolder.mkdirs()) {
						throw new Exception("File.mkdir() : Fail."); 
					}
				}
				
				File saveFile = new File(saveFilePath);
				
				
				// 파일명이 중복일 경우 파일명(1).확장자, 파일명(2).확장자 와 같은 형태로 생성
				if (saveFile.isFile()) {
					boolean _exist = true;
					
					int index = 0;
					
					// 동일한 파일명이 존재하지 않을때까지 반복
					while (_exist) {
						index++;
						
						saveFileName = randomFileName + "(" + index + ")." + fileExt;
						
						String dictFile = filePath + File.separator + saveFileName;
						
						_exist = new File(dictFile).isFile();
						
						if (!_exist) {
							savaFilePath = dictFile;
						}
					}
					
					mFile.transferTo(new File(savaFilePath));
				} else {
					// 생성한 파일 객체를 업로드 처리하지 않으면 임시파일에 저장된 파일이 자동적으로 삭제되기 때문에 transferTo(File f) 메서드를 이용해서 업로드처리
					mFile.transferTo(saveFile);
				}
				
				RefBoardFile refBoardFile =RefBoardFile.builder()
						.refBoardId(refBoardId)
						.origFileName(realFileName)
						.saveFileName(saveFileName)
						.fileSize(fileSize)
						.fileExt(fileExt)
						.filePath(filePath)
						.refDeleteYn("N")
						.build();
				
				refResultList.add(refBoardFileRepository.save(refBoardFile).getRefId());
			}
		}
		
		return (files.size() == refResultList.size()) ? true : false;
	}
	
	public int updateRefDeleteYn(Long[] deleteRefId) throws Exception {
		return refBoardFileRepository.updateRefDeleteYn(deleteRefId);
	}
	
	public int deleteRefBoardFileYn(Long[] RefBoardIdList) throws Exception {
		return refBoardFileRepository.deleteRefBoardFileYn(RefBoardIdList);
	}
}
