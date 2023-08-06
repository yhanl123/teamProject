package com.ezen.spring.project;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface MemberMapper 
{
	public int addMember(MemberVO m);
	
	public List<MemberVO> memberList();
	
	public MemberVO login(String memberID, String memberPwd);
	
	public MemberVO findId(String memberName, String memberEmail);
	
	public MemberVO findPwd(String memberName, String memberID);
	
	public MemberVO getMember(int memberNum);
	
	public MemberVO getMemberByID(String memberID);
	
	public int update(MemberVO m);
	
	public int deleteMem(int memberNum);
	
	public MemberVO getUser(String uid);
	
	public int updateUserSavingsAndPoints(MemberVO m);
	
	public int checkDuplicate(String memberID);
}
