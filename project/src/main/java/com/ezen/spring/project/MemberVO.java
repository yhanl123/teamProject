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
public class MemberVO 
{
	private int memberNum;
	private String memberName;
	private String memberPhone;
	private String memberAddress;
	private String memberID;
	private String memberPwd;
	private String memberEmail;
	private String memberSex;
	private java.sql.Date memberBirth;
	private String national;
	private int saveMoney;
	private int point;
	private int couponNum;
	private String interest;
}
