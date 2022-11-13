package com.youth.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "cart_lecture")
public class CartLecture extends BaseEntity {

	@Id
	@Column(name = "cart_lecture_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CART_LECTURE_SEQUENCE_GENERATOR")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cart_id")
	private Cart cart;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lecture_id")
	private Lecture lecture;
	
	private int count;
	
	public static CartLecture createCartLecture(Cart cart, Lecture lecture, int count) {
		CartLecture cartLecture = new CartLecture();
		cartLecture.setCart(cart);
		cartLecture.setLecture(lecture);
		cartLecture.setCount(count);
		return cartLecture;
	}
	
	public void addCount(int count) {
		this.count += count;
	}
}
