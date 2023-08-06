package com.ezen.spring.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionDAO 
{
	@Autowired
	private QuestionMapper questionMapper;
	
	public boolean addQuestion(QuestionVO q)
	{
		return questionMapper.addQuestion(q)>0;
	}
	
	public QuestionVO getQuestion(int questionNum)
	{
		return questionMapper.getQuestion(questionNum);
	}
	
	public List<QuestionVO> getList()
	{
		return questionMapper.getList();
	}
	
	public boolean updateQuestion(QuestionVO q)
	{
		return questionMapper.updateQuestion(q)>0;
	}
	
	public boolean deleteQuestion(int questionNum)
	{
		return questionMapper.deleteQuestion(questionNum)>0;
	}
}
