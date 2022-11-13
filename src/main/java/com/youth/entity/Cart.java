package com.youth.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;

@Entity
@Table(name = "cart")
@Getter
@Setter
@ToString
public class Cart extends BaseEntity {
	
	@Id
	@Column(name = "cart_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CART_SEQUENCE_GENERATOR")
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	
	public static Cart createCart(Member member) {
		Cart cart = new Cart();
		cart.setMember(member);
		return cart;
	}
	
}
