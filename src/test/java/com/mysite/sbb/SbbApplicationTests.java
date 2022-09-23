package com.mysite.sbb;

import com.mysite.sbb.question.dao.QuestionRepository;
import com.mysite.sbb.question.domain.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SbbApplicationTests {
	@Autowired
	private QuestionRepository questionRepository;
	@Test
	void contextLoads() {
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		questionRepository.save(q1);

		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);
	}

	@Test
	void getAll() {
		//TDD 테스트, 테스트 주도형개발
		//select * from question; all이라는 변수에 담김
		List<Question> all = questionRepository.findAll(); //JPA 인터페이스에 다 들어있다. 불러올수있음
		assertEquals(2,all.size());//앞에있는 애랑 뒤에있는 애랑 같은지 봄 sql에 있는 애랑

		Question oq = all.get(0); //id가 0번인 인덱스를 꺼내옴
	}

	@Test
	void getQuestionById(){
		Optional<Question> oq = questionRepository.findById(1);
		if(oq.isPresent()){
			Question q = oq.get();
			assertEquals("sbb가 무엇인가요?", q.getSubject());
		}
	}

	@Test
	void getQuestionBySubject(){
		Question q = questionRepository.findBySubject("sbb가 무엇인가요?");
		assertEquals(1, q.getId());
	}

	//쿼리 중복 "sbb가 무엇인가요?가 몇개 있는지 체크"
	@Test
	void getQuestionsBySubject(){
		List<Question> questions = this.questionRepository.findAllBySubject("sbb가 무엇인가요?");
		assertEquals(1, questions.size());
	}

}
