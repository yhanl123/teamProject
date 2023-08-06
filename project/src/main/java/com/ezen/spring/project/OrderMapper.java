package com.ezen.spring.project;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface OrderMapper 
{
	public int addOrder(OrderVO o);
	
	public List<OrderVO> orderList(String uid);
	
	public int hasPurchaseItem(String uid, int itemNum);
}
