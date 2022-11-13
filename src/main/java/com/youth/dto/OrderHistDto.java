package com.youth.dto;

import com.youth.constant.OrderStatus;
import com.youth.entity.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderHistDto {

	public OrderHistDto(Order order) {
		this.orderId = order.getId();
		this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		this.orderStatus = order.getOrderStatus();
	}
	
	private Long orderId;
	private String orderDate;
	private OrderStatus orderStatus;
	
	private List<OrderLectureDto> orderLectureDtoList = new ArrayList<>();
	
	public void addOrderLectureDto(OrderLectureDto orderLectureDto) {
		orderLectureDtoList.add(orderLectureDto);
	}
}
