package com.youth.repository;

import com.youth.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

	Cart findByMemberId(Long memberId);
}
