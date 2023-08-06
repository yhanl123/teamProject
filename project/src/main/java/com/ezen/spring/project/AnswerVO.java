package com.ezen.spring.project;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Data
public class AnswerVO 
{
	private int answerNum;
	private String answerContents;
	private String answerAuthor;
	private java.sql.Date answerDate;
	private int pQuestionNum;
}
