package com.mysite.sbb.question.controller;

import com.mysite.sbb.question.QuestionForm;
import com.mysite.sbb.question.domain.Question;
import com.mysite.sbb.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/question")
@Controller
@RequiredArgsConstructor
public class QuestionController {
    //데이터 조회하여 템플릿에 전달하기
    private final QuestionService questionService; //주입을 할 때 final을 써줘야함, 의존성주입

    //localhost:8082/만 쳐도 question/list로 URL로 페이지를 다이렉트하라는 명령
    @RequestMapping("")
    public String index(){
        return "redirect:/question/list";
    }
    @RequestMapping("/list")
    public String list(Model model) {
        List<Question> questionList = questionService.getList();
        model.addAttribute("questionList", questionList);
        return "question_list";
    }

    //단건조회
    @RequestMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id){
        System.out.println("id : "+ id);
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    //url 매핑, 질문등록하기
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm){
        return "question_form"; //question_form을 불러와야함 -> html 템플릿을 작성
    }

    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "question_form";
        }
        questionService.create(questionForm.getSubject(), questionForm.getContent());
        //questionForm안에 있는 subject, content를 가져오겠다
        return "redirect:/question/list"; //질문 저장 후 질문 목록으로 이동
    }


}
