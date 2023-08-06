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
public class ReviewAttachVO 
{
	private int reviewAttachNum;
	private String reviewAttachName;
	private int reviewAttachParentsNum;
	private float reviewFileSize;
	private String reviewFileContentType;
	
}
