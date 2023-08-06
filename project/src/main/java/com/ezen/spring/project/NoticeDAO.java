package com.ezen.spring.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Repository
public class NoticeDAO 
{
	@Autowired
	private NoticeMapper noticeMapper;
	
	public boolean addNotice(NoticeVO n) 
	{
		return noticeMapper.addNotice(n)>0;
	}
	
	public PageInfo<NoticeVO> noticeList(int pageNum)
	{
		PageHelper.startPage(pageNum, 10);
		PageInfo<NoticeVO> pageInfo = new PageInfo<>(noticeMapper.noticeList());
		return pageInfo;
	}
	
	public NoticeVO getNotice(int noticeNum)
	{
		return noticeMapper.getNotice(noticeNum);
	}
	
	public boolean editNotice(NoticeVO n)
	{
		return noticeMapper.editNotice(n)>0;
	}
	
	public boolean deleteNotice(int noticeNum)
	{
		return noticeMapper.deleteNotice(noticeNum)>0;
	}

	public PageInfo<NoticeVO> searchByNotice(String category, String keyword, int pageNum) 
	{
		PageHelper.startPage(pageNum, 10);
		
		NoticeVO key = new NoticeVO();
		if(category.equals("title")) key.setNoticeTitle(keyword);
		else if(category.equals("contents")) key.setNoticeContents(keyword);
		PageInfo<NoticeVO> pageInfo = new PageInfo<>(noticeMapper.selectByNotice(key));
		return pageInfo;
	}
}
