package com.youth.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.youth.constant.OrderStatus;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends BaseEntity {

	@Id
	@Column(name = "order_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_SQUENCE_GENERATOR")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
	
	private LocalDateTime orderDate;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
//	private LocalDateTime regTime;
//	private LocalDateTime updateTime;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<OrderLecture> orderLectures = new ArrayList<>();
	
	public void addOrderLecture(OrderLecture orderLecture) {
		orderLectures.add(orderLecture);
		orderLecture.setOrder(this);
	}
	
	public static Order createOrder(Member member, List<OrderLecture> orderLectureList) {
		Order order = new Order();
		order.setMember(member);
		
		for(OrderLecture orderLecture : orderLectureList) {
			order.addOrderLecture(orderLecture);
		}
		
		order.setOrderStatus(OrderStatus.ORDER);
		order.setOrderDate(LocalDateTime.now());
		return order;
	}
	
	public int getTotalPrice() {
		int totalPrice = 0;
		for(OrderLecture orderLecture : orderLectures) {
			totalPrice += orderLecture.getTotalPrice();
		}
		return totalPrice;
	}
	
	public void cancelOrder() {
		this.orderStatus = OrderStatus.CANCEL;
		for (OrderLecture orderLecture : orderLectures) {
			orderLecture.cancel();
		}
	}
}
