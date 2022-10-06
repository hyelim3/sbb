package com.mysite.sbb.question.service;

import com.mysite.sbb.question.dao.QuestionRepository;
import com.mysite.sbb.question.domain.Question;
import com.mysite.sbb.util.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public void create(String subject, String content) {
        //질문 서비스에 해당 기능을 추가
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        questionRepository.save(q);
    }

    public Page<Question> getList(int page){
        List<Sort.Order> sorts = new ArrayList<>(); //Sort.Order에 객체로 구성된 리스트에 객체 추가
        sorts.add(Sort.Order.desc("createDate")); //작성일시(createDate)를 역순(Desc)으로 조회
        //정수 타입의 페이지 번호를 입력받아 해당 페이지의 질문 목록을 리턴하는 메서드로 변경
        Pageable pageable = PageRequest.of(page,10, Sort.by(sorts)); // 한 페이지에 보여줄 게시글 10, 소트 객체 생성
        return questionRepository.findAll(pageable);
    }

}
