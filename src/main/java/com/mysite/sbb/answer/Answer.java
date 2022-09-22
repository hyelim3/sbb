package com.mysite.sbb.answer;

import com.mysite.sbb.question.domain.Question;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //기본키
    private Integer id;

    @Column(length = 200) //varchar 200
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    //질문이 없으면 답변이 존재할 수 없음(질문(부모)과 답변(자식) 1:N)
    @ManyToOne
    private Question question;
}
