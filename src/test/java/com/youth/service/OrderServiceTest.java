package com.youth.service;

import com.youth.constant.LectureSellStatus;
import com.youth.constant.OrderStatus;
import com.youth.dto.OrderDto;
import com.youth.entity.Lecture;
import com.youth.entity.Member;
import com.youth.entity.Order;
import com.youth.entity.OrderLecture;
import com.youth.repository.LectureRepository;
import com.youth.repository.MemberRepository;
import com.youth.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class OrderServiceTest {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	LectureRepository lectureRepository;
	
	@Autowired
	MemberRepository memberRepository;
	
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
	
//	@Test
//	@DisplayName("주문 테스트")
//	public void order() {
//		Lecture lecture = saveLecture();
//		Member member = saveMember();
//		
//		OrderDto orderDto = new OrderDto();
//		orderDto.setCount(10);
//		orderDto.setLectureId(lecture.getId());
//		
//		Long orderId = orderService.order(orderDto, member.getEmail());
//		Order order = orderRepository.findById(orderId)
//				.orElseThrow(EntityNotFoundException::new);
//		
//		List<OrderLecture> orderLectures = order.getOrderLectures();
//		
//		int totalPrice = orderDto.getCount()*lecture.getPrice();
//		
//		assertEquals(totalPrice, order.getTotalPrice());
//	}
	
	@Test
	@DisplayName("강의 취소 테스트")
	public void cancelOrder() {
		Lecture lecture = saveLecture();
		Member member = saveMember();
		
		OrderDto orderDto = new OrderDto();
		orderDto.setCount(10);
		orderDto.setLectureId(lecture.getId());
		Long orderId = orderService.order(orderDto, member.getEmail());
		
		Order order = orderRepository.findById(orderId)
				.orElseThrow(EntityNotFoundException::new);
		orderService.cancelOrder(orderId);
		
		assertEquals(OrderStatus.CANCEL, order.getOrderStatus());
	}
}
