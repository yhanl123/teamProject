package com.ezen.spring.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/coupon")
public class CouponController 
{
	@Autowired
	private CouponDAO couponDAO;
}
