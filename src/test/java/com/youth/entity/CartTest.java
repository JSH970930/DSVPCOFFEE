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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import com.youth.dto.MemberFormDto;
import com.youth.repository.CartRepository;
import com.youth.repository.MemberRepository;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
public class CartTest {

	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@PersistenceContext
	EntityManager em;
	
	public Member createMember() {
		MemberFormDto memberFormDto = new MemberFormDto();
		memberFormDto.setEmail("test@test.com");
		memberFormDto.setName("테스트");
		memberFormDto.setOneline("테스트 소개");
		memberFormDto.setPassword("1234");
		return Member.createMember(memberFormDto, passwordEncoder);
	}
	
	@Test
	@DisplayName("장바구니 회원 엔티티 매핑 조회 테스트")
	public void findCartAndMemberTest() {
		Member member = createMember();
		memberRepository.save(member);
		Cart cart = new Cart();
		cart.setMember(member);
		cartRepository.save(cart);
		
		em.flush();
		em.clear();
		
		Cart savedCart = cartRepository.findById(cart.getId())
				.orElseThrow(EntityNotFoundException::new);
		assertEquals(savedCart.getMember().getId(), member.getId());
	}
}
