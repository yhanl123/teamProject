package com.ezen.spring.project;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class ItemDAO 
{
	@Autowired
	private ItemMapper itemMapper;

	@Transactional
	public boolean addItem(ItemVO item) 
	{
		boolean itemSaved = itemMapper.addItem(item)>0;
		
		int pnum = item.getItemNum();
		
		List<ItemAttachVO> list = item.getIattList();
		for(int i=0;i<list.size();i++) {
			list.get(i).setItemParentsNum(pnum);
		}

		boolean attachSaved = false;
		if(item.getIattList().size()>0) {
			attachSaved = itemMapper.addAttach(item.getIattList())>0;
		}else {
			attachSaved = true;
		}
		log.info("itemSaved={}",itemSaved);
		log.info("attachSaved={}",attachSaved);
		return itemSaved && attachSaved;
	}
	
	public List<Map<String, String>> itemList()
	{
		return itemMapper.itemList();
	}
	
	public List<Map<String, String>> detailItem(int itemNum)
	{
		return itemMapper.detailItem(itemNum);
	}
	
	public boolean updateItem(ItemVO item)
	{
		return itemMapper.updateItem(item)>0;
	}
	
	public boolean deleteItem(int itemNum)
	{
		return itemMapper.deleteItem(itemNum)>0;
	}
}
