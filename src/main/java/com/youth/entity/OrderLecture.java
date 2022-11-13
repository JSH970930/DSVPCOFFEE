package com.youth.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class OrderLecture extends BaseEntity {

	@Id
	@Column(name = "order_lecture_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_LECTURE_SQUENCE_GENERATOR")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "lecture_id")
	private Lecture lecture;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;
	
	private int orderPrice;
	private int count;
	
	public static OrderLecture createOrderLecture(Lecture lecture, int count) {
		OrderLecture orderLecture = new OrderLecture();
		orderLecture.setLecture(lecture);
		orderLecture.setCount(count);
		orderLecture.setOrderPrice(lecture.getPrice());
		return orderLecture;
	}
	
	public int getTotalPrice() {
		return orderPrice*count;
	}

	public void cancel() {
		this.getLecture();
		
	}
}
