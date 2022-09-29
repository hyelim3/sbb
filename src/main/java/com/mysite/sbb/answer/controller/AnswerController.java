package com.mysite.sbb.answer.controller;

import com.mysite.sbb.answer.service.AnswerService;
import com.mysite.sbb.question.domain.Question;
import com.mysite.sbb.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//답변 컨트롤러 만들기
@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;

    @PostMapping("/create/{id}")
    //request 메소드 상관없이 처리, post요청으로 온 건만 처리
    //id로 질문을 불러옴, content 받아야함
   public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam String content){
        //질문 하나 가져오기
        Question question = questionService.getQuestion(id);
        //ToDo : 답변생성
        answerService.createAnswer(question, content);
        return String.format("redirect:/question/detail/%s", id);
    }

}
