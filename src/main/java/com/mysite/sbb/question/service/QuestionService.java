package com.mysite.sbb.question.service;

import com.mysite.sbb.question.dao.QuestionRepository;
import com.mysite.sbb.question.domain.Question;
import com.mysite.sbb.util.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<Question> getList(){ //getList 반환하는 함수
        return questionRepository.findAll();
    }

    //단건조회 한개 조회
    public Question getQuestion(Integer id){
        Optional<Question> questionOptional = questionRepository.findById(id);
        if(questionOptional.isPresent()){
            return  questionOptional.get();
        }else{
            throw new DataNotFoundException("question not found");
        }
    }
}
