package com.ezen.spring.project;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class ReviewSvc 
{
	@Autowired
	private ReviewDAO reviewDAO;
	
	public ReviewVO detailReview(int reviewNum)
	{
		List<Map<String, String>> list = reviewDAO.detailReview(reviewNum);
		
		ReviewVO r = getReviewIn(list.get(0));
		r.setRattList(new ArrayList<ReviewAttachVO>());
		
		for(int i=0;i<list.size();i++) { //list.size() 첨부파일의 갯수와 일치
			Map<String, String> m = list.get(i);
			
			List<ReviewAttachVO> attList = getReviewAttachIn(m);
			r.setRattList(attList);
		}
		return r;
	}
	
	private List<ReviewAttachVO> getReviewAttachIn(Map m)
	{
		if(m.get("reviewAttachNames")==null) return null;
		
		String reviewAttachNums = m.get("reviewAttachNums").toString();
		String reviewAttachNames = m.get("reviewAttachNames").toString();
		
		String[] nums = reviewAttachNums.split(",");
		String[] names = reviewAttachNames.split(",");
		
		List<ReviewAttachVO> rattlist = new ArrayList<>();
		for(int i=0; i<nums.length; i++) {
			int reviewattNum = Integer.parseInt(nums[i]);
			String reviewattName = names[i];
			
			ReviewAttachVO ratt = new ReviewAttachVO();
			ratt.setReviewAttachNum(reviewattNum);
			ratt.setReviewAttachName(reviewattName);
			
			rattlist.add(ratt);
		}
		
		return rattlist;
	}
	
	private ReviewVO getReviewIn(Map m)
	{
		int reviewNum = Integer.parseInt(m.get("reviewNum").toString());
		String reviewTitle = m.get("reviewTitle").toString();
		String reviewContents = m.get("reviewContents").toString();
		String reviewAuthor = m.get("reviewAuthor").toString();
		java.sql.Date reviewDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			reviewDate = new java.sql.Date(sdf.parse(m.get("reviewDate").toString()).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int reviewLikeCnt = Integer.parseInt(m.get("reviewLikeCnt").toString());
		
		ReviewVO r = new ReviewVO();
		r.setReviewNum(reviewNum);
		r.setReviewTitle(reviewTitle);
		r.setReviewContents(reviewContents);
		r.setReviewAuthor(reviewAuthor);
		r.setReviewDate(reviewDate);
		r.setReviewLikeCnt(reviewLikeCnt);
		
		return r;
	}
	
	@Transactional
	public boolean removeAttach(int reviewAttachNum, HttpServletRequest request)
	{
		ServletContext context = request.getServletContext();
		String savePath = context.getRealPath("/reviewPhoto");
		ReviewAttachVO ra = reviewDAO.getAttach(reviewAttachNum);
		String fname = ra.getReviewAttachName();
		File delFile = new File(savePath, fname);
		
		boolean deleted = reviewDAO.removeAttach(reviewAttachNum);
		boolean fdeleted = delFile.delete();

		return deleted && fdeleted;
	}
}
