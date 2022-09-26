package com.mysite.sbb;

import com.mysite.sbb.answer.dao.AnswerRepository;
import com.mysite.sbb.answer.domain.Answer;
import com.mysite.sbb.question.dao.QuestionRepository;
import com.mysite.sbb.question.domain.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SbbApplicationTests {
	@Autowired
	private QuestionRepository questionRepository;

	//답변을 처리하기 위해서는 답변 리포지터리가 필요
	@Autowired
	private AnswerRepository answerRepository;

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

	//subject와 content 둘다 만족하는 거 찾기
	@Test
	void findBySubjectAndContent(){
		List<Question> questions = this.questionRepository.findBySubjectAndContent("sbb가 무엇인가요?","sbb에 대해 알고 싶습니다.");
		assertEquals(1, questions.size());
	}

	//subject 내용이 뭔가를 포함하는 거 찾기
	@Test
	void findBySubjectLike(){
		List<Question> questions = this.questionRepository.findBySubjectLike("%sbb%");
		assertEquals(1, questions.size());
	}

	//데이터 수정하기 set
	@Test
	void updateQuestion() {
		Optional <Question> oq = questionRepository.findById(2); //2번 id를 수정하겠다
		if(oq.isPresent()){ //만약 값이 있으면
			Question question = oq.get(); //가져오기
			question.setSubject("수정된 질문");
			question.setContent("수정된 내용");
			questionRepository.save(question); //변경된 내용을 question에 저장
		}
	}
	//데이터 삭제하기
	@Test
	void deleteQuetion(){
		assertEquals(4, questionRepository.count()); //해당 리포지터리의 총 데이터건수를 리턴한다.
		Optional <Question> oq = questionRepository.findById(1); //1번 데이터를 찾아오겠다.
		if(oq.isPresent()){
			Question question = oq.get();
			questionRepository.delete(question);
		}
		assertEquals(3, questionRepository.count()); //삭제하기 전에는 4개, 삭제 후 3개
	}

	//답변 데이터 생성 후 저장하기
	@Test
	void createAnswer(){
		Optional <Question> oq = questionRepository.findById(2); //2번 질문에 대한 답변
		if(oq.isPresent()){
			Question question = oq.get();

			Answer answer = new Answer();
			answer.setContent("답변입니다.");
			answer.setQuestion(question); //어떤 질문에 답변인지 알기 위해서 Question 객체가 필요하다.
			answer.setCreateDate(LocalDateTime.now());
			answerRepository.save(answer);
		}
	}

	//질문에서 답변목록 불러오기
	@Test
	@Transactional
	//DB연결이 끝날 때까지 안끝난다.
	void getAnswerByQuestion(){
		Optional<Question> oq = questionRepository.findById(2);
		if(oq.isPresent()){
			Question question = oq.get();
			List<Answer> answerList = question.getAnswerList();
			assertEquals(1, answerList.size());
		}
	}

}
