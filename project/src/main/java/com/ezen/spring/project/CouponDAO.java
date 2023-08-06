package com.ezen.spring.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CouponDAO 
{
	@Autowired
	private CouponMapper couponMapper;
}
