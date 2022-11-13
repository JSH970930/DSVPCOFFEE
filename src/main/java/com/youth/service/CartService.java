package com.youth.service;

import com.youth.dto.CartDetailDto;
import com.youth.dto.CartLectureDto;
import com.youth.entity.Cart;
import com.youth.entity.CartLecture;
import com.youth.entity.Lecture;
import com.youth.entity.Member;
import com.youth.repository.CartLectureRepository;
import com.youth.repository.CartRepository;
import com.youth.repository.LectureRepository;
import com.youth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

	private final LectureRepository lectureRepository;
	private final MemberRepository memberRepository;
	private final CartRepository cartRepository;
	private final CartLectureRepository cartLectureRepository;
	
	public Long addCart(CartLectureDto cartLectureDto, String email) {
		Lecture lecture = lectureRepository.findById(cartLectureDto.getLectureId())
				.orElseThrow(EntityNotFoundException::new);
		Member member = memberRepository.findByEmail(email);
		
		Cart cart = cartRepository.findByMemberId(member.getId());
		if(cart == null) {
			cart = Cart.createCart(member);
			cartRepository.save(cart);
		}
		
		CartLecture savedCartLecture = cartLectureRepository.findByCartIdAndLectureId(cart.getId(), lecture.getId());
		if(savedCartLecture != null) {
			savedCartLecture.addCount(cartLectureDto.getCount());
			return savedCartLecture.getId();
		} else {
			CartLecture cartLecture = CartLecture.createCartLecture(cart, lecture, cartLectureDto.getCount());
			cartLectureRepository.save(cartLecture);
			return cartLecture.getId();
		}
	}
	
	
}
