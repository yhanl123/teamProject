package com.ezen.spring.project;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface NoticeMapper 
{
	public int addNotice(NoticeVO n);
	
	public List<NoticeVO> noticeList();
	
	public NoticeVO getNotice(int noticeNum);
	
	public int editNotice(NoticeVO n);
	
	public int deleteNotice(int noticeNum);
	
	public List<NoticeVO> selectByNotice(NoticeVO key);
}
