package com.youth.service;

import com.youth.constant.LectureSellStatus;
import com.youth.dto.LectureFormDto;
import com.youth.entity.Lecture;
import com.youth.entity.LectureImg;
import com.youth.repository.LectureImgRepository;
import com.youth.repository.LectureRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.EntityNotFoundException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class LectureServiceTest {

	@Autowired
	LectureService lectureService;
	
	@Autowired
	LectureRepository lectureRepository;
	
	@Autowired
	LectureImgRepository lectureImgRepository;
	
	List<MultipartFile> createMultipartFiles() throws Exception {
		List<MultipartFile> multipartFileList = new ArrayList<>();
		
		for(int i = 0; i < 5; i++) {
			String path = "C:/youth/lecture/";
			String imageName = "image" + i + ".jpg";
			MockMultipartFile multipartFile = new MockMultipartFile(path, imageName, "image/jpg", new byte[]{1,2,3,4});
			multipartFileList.add(multipartFile);
		}
		return multipartFileList;
	}
	
	@Test
	@DisplayName("강의 등록 테스트")
	@WithMockUser(username = "admin", roles= "ADMIN")
	void saveLecture() throws Exception {
		LectureFormDto lectureFormDto = new LectureFormDto();
		lectureFormDto.setLectureNm("테스트 강의");
		lectureFormDto.setLectureSellStatus(LectureSellStatus.OPEN);
		lectureFormDto.setLectureDetail("테스트 강의입니다.");
		lectureFormDto.setPrice(1000);
		
		List<MultipartFile> multipartFileList = createMultipartFiles();
		Long lectureId = lectureService.saveLecture(lectureFormDto, multipartFileList);
		
		List<LectureImg> lectureImgList = lectureImgRepository.findByLectureIdOrderByIdAsc(lectureId);
		
		Lecture lecture = lectureRepository.findById(lectureId)
				.orElseThrow(EntityNotFoundException::new);
		
		assertEquals(lectureFormDto.getLectureNm(), lecture.getLectureNm());
		assertEquals(lectureFormDto.getLectureSellStatus(), lecture.getLectureSellStatus());
		assertEquals(lectureFormDto.getLectureDetail(), lecture.getLectureDetail());
		assertEquals(lectureFormDto.getPrice(), lecture.getPrice());
		assertEquals(multipartFileList.get(0).getOriginalFilename(), lectureImgList.get(0).getOriImgName());
	}
}
