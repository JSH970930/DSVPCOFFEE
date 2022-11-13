package com.youth.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.youth.constant.LectureSellStatus;
import com.youth.dto.LectureSearchDto;
import com.youth.dto.MainLectureDto;
import com.youth.dto.QMainLectureDto;
import com.youth.entity.Lecture;
import com.youth.entity.QLecture;
import com.youth.entity.QLectureImg;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class LectureRepositoryCustomImpl implements LectureRepositoryCustom {
	
	private JPAQueryFactory queryFactory;
	
	public LectureRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	private BooleanExpression searchSellStatusEq(LectureSellStatus searchSellStatus) {
		return searchSellStatus == null ? null : QLecture.lecture.lectureSellStatus.eq(searchSellStatus);
	}
	
	private BooleanExpression regDtsAfter(String searchDateType) {
		LocalDateTime dateTime = LocalDateTime.now();
		
		if(StringUtils.equals("all", searchDateType) || searchDateType == null) {
			return null;
		} else if(StringUtils.equals("1d", searchDateType)) {
			dateTime = dateTime.minusDays(1);
		} else if(StringUtils.equals("1w", searchDateType)) {
			dateTime = dateTime.minusWeeks(1);
		} else if(StringUtils.equals("1m", searchDateType)) {
			dateTime = dateTime.minusMonths(1);
		} else if(StringUtils.equals("6m", searchDateType)) {
			dateTime = dateTime.minusMonths(6);
		}
		return QLecture.lecture.regTime.after(dateTime);
	}
	
	private BooleanExpression searchByLike(String searchBy, String searchQuery) {
		if(StringUtils.equals("lectureNm", searchBy)) {
			return QLecture.lecture.lectureNm.like("%" + searchQuery + "%");
		} else if (StringUtils.equals("createdBy", searchBy)) {
			return QLecture.lecture.createdBy.like("%" + searchQuery + "%");
		}
		return null;
	}
	
	@Override
	public Page<Lecture> getAdminLecturePage(LectureSearchDto lectureSearchDto, Pageable pageable) {
		
		QueryResults<Lecture> results = queryFactory
				.selectFrom(QLecture.lecture)
				.where(regDtsAfter(lectureSearchDto.getSearchDateType()),
						searchSellStatusEq(lectureSearchDto.getSearchSellStatus()),
						searchByLike(lectureSearchDto.getSearchBy(),
								lectureSearchDto.getSearchQuery()))
				.orderBy(QLecture.lecture.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetchResults();
		
		List<Lecture> content = results.getResults();
		long total = results.getTotal();
		
		return new PageImpl<>(content, pageable, total);
	}
	
	public BooleanExpression lectureNmLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ? null : QLecture.lecture.lectureNm.like("%" + searchQuery + "%");
	}
	
	@Override
	public Page<MainLectureDto> getMainLecturePage(LectureSearchDto lectureSearchDto, Pageable pageable) {
		QLecture lecture = QLecture.lecture;
		QLectureImg lectureImg = QLectureImg.lectureImg;
		
		QueryResults<MainLectureDto> results = queryFactory
				.select(
						new QMainLectureDto(
								lecture.id,
								lecture.lectureNm,
								lecture.lectureDetail,
								lectureImg.imgUrl,
								lecture.price)
				)
				.from(lectureImg)
				.join(lectureImg.lecture, lecture)
				.where(lectureImg.repImgYn.eq("Y"))
				.where(lectureNmLike(lectureSearchDto.getSearchQuery()))
				.orderBy(lecture.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetchResults();
		
		List<MainLectureDto> content = results.getResults();
		long total = results.getTotal();
		return new PageImpl<>(content, pageable, total);
	}

}
