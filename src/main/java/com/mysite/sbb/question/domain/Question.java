package com.mysite.sbb.question.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mysite.sbb.answer.domain.Answer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //기본키
    private Integer id;

    @Column(length = 200) //varchar 200
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Answer> answerList;
}
