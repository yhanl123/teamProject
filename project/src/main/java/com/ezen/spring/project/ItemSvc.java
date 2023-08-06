package com.ezen.spring.project;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemSvc 
{
	@Autowired
	private ItemDAO itemDAO;
	
	public ItemVO detailItem(int itemNum)
	{
		List<Map<String,String>> list = itemDAO.detailItem(itemNum);
		
		ItemVO item = getItemIn(list.get(0));
		item.setIattList(new ArrayList<ItemAttachVO>());
		
		for(int i=0;i<list.size();i++) { //list.size() 첨부파일의 갯수와 일치
			Map<String, String> m = list.get(i);
			
			List<ItemAttachVO> attList = getItemAttachIn(m);
			item.setIattList(attList);;
		}
		return item;
	}

	private List<ItemAttachVO> getItemAttachIn(Map m) 
	{
		if(m.get("itemAttachNames")==null) return null;
		
		String itemAttachNums = m.get("itemAttachNums").toString();
		String itemAttachNames = m.get("itemAttachNames").toString();
		String itemFileSizes = m.get("itemFileSizes").toString();
		
		String[] sNums = itemAttachNums.split(",");
		String[] sNames = itemAttachNames.split(",");
		String[] sSizes = itemFileSizes.split(",");
		
		List<ItemAttachVO> iattlist = new ArrayList<>();
		for(int i=0;i<sNums.length;i++) {
			int fnum = Integer.parseInt(sNums[i]);
			String fname = sNames[i];
			float fsize = Float.parseFloat(sSizes[i]);
			
			ItemAttachVO a = new ItemAttachVO();
			a.setItemAttachNum(fnum);
			a.setItemAttachName(fname);
			a.setItemFileSize(fsize);
			
			iattlist.add(a);
		}
		
		return iattlist;
	}

	private ItemVO getItemIn(Map m) 
	{
		int itemNum = Integer.parseInt(m.get("itemNum").toString());
		String goods = m.get("goods").toString();
		int price = Integer.parseInt(m.get("price").toString());
		String explains= m.get("explains").toString();
		
		ItemVO item = new ItemVO();
		item.setItemNum(itemNum);
		item.setGoods(goods);
		item.setPrice(price);
		item.setExplains(explains);
		
		return item;
	}
}
