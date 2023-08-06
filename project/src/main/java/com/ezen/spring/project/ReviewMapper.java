package com.ezen.spring.project;

import java.util.*;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface ReviewMapper 
{
	public int addReview(ReviewVO r);
	
	public int addAttachReview(List<ReviewAttachVO> ratt);
	
	public List<Map<String, String>> reviewList(int itemNum);
	
	public List<Map<String, String>> detailReview(int reviewNum);
	
	public int removeAttach(int reviewAttachNum);
	
	public ReviewAttachVO getAttach(int reviewAttachNum);
	
	public int updateReview(ReviewVO r);
	
	public int deleteReview(int reviewNum);
	
	public int likeCnt(int reviewNum);
}
