package com.ezen.spring.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAO 
{
	@Autowired
	private MemberMapper memberMapper;
	
	public boolean addMember(MemberVO m)
	{
		return memberMapper.addMember(m)>0;
	}
	
	public List<MemberVO> memberList()
	{
		return memberMapper.memberList();
	}
	
	public boolean login(String memberID, String memberPwd)
	{
		MemberVO member = memberMapper.login(memberID, memberPwd);
	    return member != null;
	}
	
	public MemberVO findId(String memberName, String memberEmail)
	{
		return memberMapper.findId(memberName, memberEmail);
	}
	
	public MemberVO findPwd(String memberName, String memberID)
	{
		return memberMapper.findPwd(memberName, memberID);
	}
	
	public MemberVO getMember(int memberNum)
	{
		return memberMapper.getMember(memberNum);
	}
	
	public MemberVO getMemberByID(String memberID)
	{
		return memberMapper.getMemberByID(memberID);
	}
	
	public boolean update(MemberVO m)
	{
		return memberMapper.update(m)>0;
	}
	
	public boolean deleteMem(int memberNum)
	{
		return memberMapper.deleteMem(memberNum)>0;
	}
	
	public MemberVO getUser(String uid)
	{
		return memberMapper.getUser(uid);
	}
	
	public boolean updateUserSavingsAndPoints(MemberVO m)
	{
		return memberMapper.updateUserSavingsAndPoints(m)>0;
	}

	public boolean checkDuplicate(String memberID) 
	{		
		return memberMapper.checkDuplicate(memberID)>0;
	}
}
