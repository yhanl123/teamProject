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
public class CouponVO 
{
	private int cNum;
	private String cName;
	private int discount;
	private java.sql.Date expiryDate;
}
