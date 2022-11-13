package com.youth.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.youth.entity.CartLecture;

public interface CartLectureRepository extends JpaRepository<CartLecture, Long> {

	CartLecture findByCartIdAndLectureId(Long cartId, Long lectureId);
	
	
}
