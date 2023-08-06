package com.ezen.spring.project;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface AnswerMapper 
{
	public int addAnswer(AnswerVO a);
	
	public AnswerVO getAnswerByQuestion(int questionNum);
	
	public AnswerVO getAnswer(int answerNum);
	
	public int updateAnswer(AnswerVO a);
	
	public int deleteAnswer(int answerNum);
	
	public int checkAnswerExistsForQuestion(int pQuestionNum);
	
}
