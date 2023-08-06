package com.ezen.spring.project;

import java.util.List;

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
public class ReviewVO 
{
	private int reviewNum;
	private String reviewTitle;
	private String reviewContents;
	private java.sql.Date reviewDate;
	private int reviewParentsNum;
	private int reviewLikeCnt;
	private String reviewAuthor;
	
	private List<ReviewAttachVO> rattList;
}
