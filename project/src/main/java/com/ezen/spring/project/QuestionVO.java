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
public class QuestionVO 
{
	private int questionNum;
	private String questionTitle;
	private String questionAuthor;
	private java.sql.Date questionDate;
	private String questionContents;
	
}
