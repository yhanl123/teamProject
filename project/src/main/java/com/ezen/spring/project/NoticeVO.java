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
public class NoticeVO 
{
	private int noticeNum;
	private String noticeTitle;
	private String noticeAuthor;
	private java.sql.Date noticeDate;
	private String noticeContents;
}
