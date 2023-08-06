package com.ezen.spring.project;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface QuestionMapper 
{
	public int addQuestion(QuestionVO q);
	
	public QuestionVO getQuestion(int questionNum);
	
	public List<QuestionVO> getList();
	
	public int updateQuestion(QuestionVO q);
	
	public int deleteQuestion(int questionNum);
}
