package com.ezen.spring.project;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface ItemMapper 
{
	public int addItem(ItemVO item);
	
	public int addAttach(List<ItemAttachVO> iatt);
	
	public List<Map<String, String>> itemList();
	
	public List<Map<String, String>> detailItem(int itemNum);
	
	public int updateItem(ItemVO item);
	
	public int deleteItem(int itemNum);
	
	public int purchaseCnt(OrderVO o);
	
	public int cartCnt(String goods);
}
