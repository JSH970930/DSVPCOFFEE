package com.youth.service;

import com.youth.constant.LectureSellStatus;
import com.youth.dto.CartLectureDto;
import com.youth.entity.CartLecture;
import com.youth.entity.Lecture;
import com.youth.entity.Member;
import com.youth.repository.CartLectureRepository;
import com.youth.repository.LectureRepository;
import com.youth.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class CartServiceTest {

	@Autowired
	LectureRepository lectureRepository;
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	CartService cartService;
	
	@Autowired
	CartLectureRepository cartLectureRepository;
	
	public Lecture saveLecture() {
		Lecture lecture = new Lecture();
		lecture.setLectureNm("테스트 강의");
		lecture.setPrice(10000);
		lecture.setLectureDetail("테스트 강의 상세 설명");
		lecture.setLectureSellStatus(LectureSellStatus.OPEN);
		return lectureRepository.save(lecture);
	}
	
	public Member saveMember() {
		Member member = new Member();
		member.setEmail("test@test.com");
		return memberRepository.save(member);
	}
	
	@Test
	@DisplayName("장바구니 담기 테스트")
	public void addCart() {
		Lecture lecture = saveLecture();
		Member member = saveMember();
		
		CartLectureDto cartLectureDto = new CartLectureDto();
		cartLectureDto.setCount(5);
		cartLectureDto.setLectureId(lecture.getId());
		
		Long cartLectureId = cartService.addCart(cartLectureDto, member.getEmail());
		CartLecture cartLecture = cartLectureRepository.findById(cartLectureId)
				.orElseThrow(EntityNotFoundException::new);
		
		assertEquals(lecture.getId(), cartLecture.getLecture().getId());
		assertEquals(cartLectureDto.getCount(), cartLecture.getCount());
	}
}
