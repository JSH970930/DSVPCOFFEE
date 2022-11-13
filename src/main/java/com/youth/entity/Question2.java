package com.youth.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


import com.youth.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Question2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question2", cascade = CascadeType.REMOVE)
    private List<Answer2> answer2List;
    
    @ManyToOne
    private Member author;
    
    private LocalDateTime modifyDate;
}