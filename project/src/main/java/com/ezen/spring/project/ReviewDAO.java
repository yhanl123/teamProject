package com.ezen.spring.project;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Repository
public class ReviewDAO 
{
	@Autowired
	private ReviewMapper reviewMapper;
	
	public boolean addReview(ReviewVO r)
	{
		boolean rSaved = reviewMapper.addReview(r)>0;
		
		int pnum = r.getReviewNum();
		List<ReviewAttachVO> list = r.getRattList();
		for(int i=0;i<list.size();i++) {
			list.get(i).setReviewAttachParentsNum(pnum);
		}
		
		boolean attachSaved = false;
		if(r.getRattList().size()>0) {
			attachSaved = reviewMapper.addAttachReview(r.getRattList())>0;
		}else {
			attachSaved = true;
		}
		
		return rSaved && attachSaved;
	}
	
	public PageInfo<Map> reviewList(int itemNum, int pageNum)
	{
		PageHelper.startPage(pageNum, 2);
		PageInfo<Map> pageInfo = new PageInfo<>(reviewMapper.reviewList(itemNum));
		return pageInfo;
	}
	
	public List<Map<String, String>> detailReview(int reviewNum)
	{
		return reviewMapper.detailReview(reviewNum);
	}
	
	public boolean removeAttach(int reviewAttachNum)
	{
		return reviewMapper.removeAttach(reviewAttachNum)>0;
	}
	
	public ReviewAttachVO getAttach(int reviewAttachNum)
	{
		return reviewMapper.getAttach(reviewAttachNum);
	}
	
	public boolean addAttachReview(List<ReviewAttachVO> list)
	{
		return reviewMapper.addAttachReview(list)>0;
	}
	
	public boolean updateReview(ReviewVO r)
	{
		return reviewMapper.updateReview(r)>0;
	}
	
	public boolean deleteReview(int reviewNum)
	{
		return reviewMapper.deleteReview(reviewNum)>0;
	}
	
	public boolean likeCnt(int reviewNum)
	{
		return reviewMapper.likeCnt(reviewNum)>0;
	}
}
