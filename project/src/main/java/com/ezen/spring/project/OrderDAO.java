package com.ezen.spring.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class OrderDAO 
{
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private ItemMapper itemMapper;
	
	@Transactional
	public boolean addOrder(OrderVO o)
	{
		itemMapper.purchaseCnt(o);
		return orderMapper.addOrder(o)>0;
	}
	
	public List<OrderVO> orderList(String uid)
	{
		return orderMapper.orderList(uid);
	}
	
	public boolean hasPurchaseItem(String uid, int itemNum)
	{
		return orderMapper.hasPurchaseItem(uid, itemNum)>0;
	}
}
