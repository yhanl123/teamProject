package com.ezen.spring.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerDAO 
{
	@Autowired
	private AnswerMapper answerMapper;
	
	public boolean addAnswer(AnswerVO a)
	{
		return answerMapper.addAnswer(a)>0;
	}
	
	public AnswerVO getAnswerByQuestion(int questionNum)
	{
		return answerMapper.getAnswerByQuestion(questionNum);
	}
	
	public AnswerVO getAnswer(int answerNum)
	{
		return answerMapper.getAnswer(answerNum);
	}
	
	public boolean updateAnswer(AnswerVO a) 
	{
	    return answerMapper.updateAnswer(a)>0;
	}

	public boolean deleteAnswer(int answerNum) 
	{
	    return answerMapper.deleteAnswer(answerNum)>0;
	}

	public boolean checkAnswerExistsForQuestion(int pQuestionNum) 
	{
		return answerMapper.checkAnswerExistsForQuestion(pQuestionNum)>0;
	}
}
