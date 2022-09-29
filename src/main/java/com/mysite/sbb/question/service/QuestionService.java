package com.mysite.sbb.question.service;

import com.mysite.sbb.question.dao.QuestionRepository;
import com.mysite.sbb.question.domain.Question;
import com.mysite.sbb.util.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<Question> getList(){ //getList 반환하는 함수
        return questionRepository.findAll();
    }

    //단건조회 한개 조회
    public Question getQuestion(Integer id){
        return questionRepository.findById(id).orElseThrow(()-> new DataNotFoundException("question not found"));
    }
//        Optional<Question> questionOptional = questionRepository.findById(id);
//        if(questionOptional.isPresent()){
//            return  questionOptional.get();
//        }else{
//            throw new DataNotFoundException("question not found");
//        }
//    }
    //짧게 쓸 수 있다. 람다식
}
