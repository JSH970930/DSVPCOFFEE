package com.youth.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.youth.constant.LectureSellStatus;
import com.youth.repository.LectureRepository;
import com.youth.repository.MemberRepository;
import com.youth.repository.OrderLectureRepository;
import com.youth.repository.OrderRepository;

@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
@Transactional
class OrderTest {

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	LectureRepository lectureRepository;
	
	@PersistenceContext
	EntityManager em;
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	OrderLectureRepository orderLectureRepository;
	
	public Lecture createLecture() {
		Lecture lecture = new Lecture();
		lecture.setLectureNm("테스트 강의");
		lecture.setPrice(10000);
		lecture.setLectureDetail("상세 설명");
		lecture.setLectureSellStatus(LectureSellStatus.OPEN);
		
		return lecture;
	}
	
	public Order createOrder() {
		Order order = new Order();
		for(int i = 0; i < 3; i++) {
			Lecture lecture = createLecture();
			lectureRepository.save(lecture);
			OrderLecture orderLecture = new OrderLecture();
			orderLecture.setLecture(lecture);
			orderLecture.setCount(10);
			orderLecture.setOrderPrice(10000);
			orderLecture.setOrder(order);
			order.getOrderLectures().add(orderLecture);
		}
		Member member = new Member();
		memberRepository.save(member);
		order.setMember(member);
		orderRepository.save(order);
		return order;
	}
	
//	@Test
//	@DisplayName("영속성 전이 테스트")
//	public void cascadeTest() {
//		Order order = new Order();
//		
//		for(int i = 0; i < 3; i++) {
//			Lecture lecture = this.createLecture();
//			lectureRepository.save(lecture);
//			OrderLecture orderLecture = new OrderLecture();
//			orderLecture.setLecture(lecture);
//			orderLecture.setCount(10);
//			orderLecture.setOrderPrice(1000);
//			orderLecture.setOrder(order);
//			order.getOrderLectures().add(orderLecture);
//		}
//		
//		orderRepository.saveAndFlush(order);
//		em.clear();
//		
//		Order savedOrder = orderRepository.findById(order.getId())
//				.orElseThrow(EntityNotFoundException::new);
//		assertEquals(3, savedOrder.getOrderLectures().size());
//	}
	
//	@Test
//	@DisplayName("고아객체 제거 테스트")
//	public void orphanRemovalTest() {
//		Order order = this.createOrder();
//		order.getOrderLectures().remove(0);
//		em.flush();
//	}
	
	@Test
	@DisplayName("지연 로딩 테스트")
	public void lazyLoadingTest() {
		Order order = this.createOrder();
		Long orderLectureId = order.getOrderLectures().get(0).getId();
		em.flush();
		em.clear();
		OrderLecture orderLecture = orderLectureRepository.findById(orderLectureId)
				.orElseThrow(EntityNotFoundException::new);
		System.out.println("Order class : " + orderLecture.getOrder().getClass());
		System.out.println("================================");
		orderLecture.getOrder().getOrderDate();
		System.out.println("================================");
	}
}
