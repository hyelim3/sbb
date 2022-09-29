package com.mysite.sbb.answer.service;

import com.mysite.sbb.answer.dao.AnswerRepository;
import com.mysite.sbb.answer.domain.Answer;
import com.mysite.sbb.question.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

//답변 저장하기
@RequiredArgsConstructor
@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    public void createAnswer(Question question, String content){
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answerRepository.save(answer);
    }
}
