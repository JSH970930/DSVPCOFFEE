package com.youth.service;

import com.youth.dto.OrderDto;
import com.youth.dto.OrderHistDto;
import com.youth.dto.OrderLectureDto;
import com.youth.entity.*;
import com.youth.repository.LectureImgRepository;
import com.youth.repository.LectureRepository;
import com.youth.repository.MemberRepository;
import com.youth.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor

public class OrderService {

	private final LectureRepository lectureRepository;
	private final MemberRepository memberRepository;
	private final OrderRepository orderRepository;
	private final LectureImgRepository lectureImgRepository;
	
	public Long order(OrderDto orderDto, String email) {
		Lecture lecture = lectureRepository.findById(orderDto.getLectureId())
				.orElseThrow(EntityNotFoundException::new);
		
		Member member = memberRepository.findByEmail(email);
		
		List<OrderLecture> orderLectureList = new ArrayList<>();
		OrderLecture orderLecture = OrderLecture.createOrderLecture(lecture, orderDto.getCount());
		
		orderLectureList.add(orderLecture);
		Order order = Order.createOrder(member, orderLectureList);
		
		orderRepository.save(order);
		
		return order.getId();
	}
	
	@Transactional(readOnly = true)
	public Page<OrderHistDto> getOrderList(String email, Pageable pageable) {
		
		List<Order> orders = orderRepository.findOrders(email, pageable);
		Long totalCount = orderRepository.countOrder(email);
		
		List<OrderHistDto> orderHistDtos = new ArrayList<>();
		
		for (Order order : orders) {
			OrderHistDto orderHistDto = new OrderHistDto(order);
			List<OrderLecture> orderLectures = order.getOrderLectures();
			for (OrderLecture orderLecture : orderLectures) {
				LectureImg lectureImg = lectureImgRepository.findByLectureIdAndRepImgYn(orderLecture.getLecture().getId(), "Y");
				OrderLectureDto orderLectureDto = new OrderLectureDto(orderLecture, lectureImg.getImgUrl());
				orderHistDto.addOrderLectureDto(orderLectureDto);
			}
			
			orderHistDtos.add(orderHistDto);
		}
		
		return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount);
	}
	
	@Transactional(readOnly = true)
	public boolean validateOrder(Long orderId, String email) {
		Member curMember = memberRepository.findByEmail(email);
		Order order = orderRepository.findById(orderId)
				.orElseThrow(EntityNotFoundException::new);
		Member savedMember = order.getMember();
		
		if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())) {
			return false;
		}
		
		return true;
	}
	
	public void cancelOrder(Long orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(EntityNotFoundException::new);
		order.cancelOrder();
	}
}
