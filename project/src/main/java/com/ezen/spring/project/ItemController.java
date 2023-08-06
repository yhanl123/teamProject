package com.ezen.spring.project;

import java.io.File;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/item")
@Slf4j
@SessionAttributes("userid")
public class ItemController 
{
	@Autowired
	private ItemDAO itemDAO;
	
	@Autowired
	private ItemSvc itemSvc;
	
	@Autowired
	private OrderDAO orderDAO;
	
	@GetMapping("/")
	public String index()
	{
		return "item/itemIndex";
	}
	
	@GetMapping("/add")
	public String addItem()
	{
		return "item/addItem";
	}
	
	@PostMapping("/add")
	@ResponseBody
	public Map<String, Object> addResult(ItemVO item, @RequestParam("files")MultipartFile[] mfiles,
			HttpServletRequest request)
	{
		ServletContext context = request.getServletContext();
		String savePath = context.getRealPath("/items");
		
		List<ItemAttachVO> iattList = new ArrayList<>();
		
		try {
			for(int i=0;i<mfiles.length;i++) {
				if(mfiles[0].getSize()==0) continue;
				String originName = mfiles[i].getOriginalFilename();
				mfiles[i].transferTo(new File(savePath+"/"+originName));
				
				/* MultipartFile 주요 메소드 */
				String cType = mfiles[i].getContentType();
				String pName = mfiles[i].getName();
				Resource res = mfiles[i].getResource();
				long fSize = mfiles[i].getSize();
				boolean empty = mfiles[i].isEmpty();
				
				ItemAttachVO iatt = new ItemAttachVO();
				iatt.setItemAttachName(originName);
				iatt.setItemFileSize(fSize/1024);
				iatt.setItemFileContentType(cType);
				
				iattList.add(iatt);
			}
			
			item.setIattList(iattList);
			boolean added = itemDAO.addItem(item);
			Map<String, Object> map = new HashMap<>();
			map.put("added", added);
			map.put("PKNum", item.getItemNum());
			log.info("added={}",added);
			
			return map;
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		Map<String, Object> map = new HashMap<>();
		map.put("added", false);
		return map;
	}
	
	@GetMapping("/list") //<a href="/list/1?category=상의">상의</a>
	public String itemList(Model model)
	{
		List<Map<String, String>> list = itemDAO.itemList();
		model.addAttribute("list", list);
		return "item/itemList";
	}
	
	@GetMapping("/get/{itemNum}")
	public String detailItem(@PathVariable int itemNum, @RequestParam(defaultValue = "1") int pn, Model model, @SessionAttribute(value="userid", required=false) String uid) {
	    ItemVO item = itemSvc.detailItem(itemNum);
	    model.addAttribute("item", item);
	    model.addAttribute("userid", uid);
	    model.addAttribute("pn", pn);
	    
	    boolean hasPurchased = false;
	    
	    if(uid != null) {
	    	hasPurchased =orderDAO.hasPurchaseItem(uid, itemNum);
	    }
	    model.addAttribute("hasPurchased", hasPurchased);
	    return "item/detailItem";
	}
	
	@GetMapping("/update/{itemNum}")
	public String update(@PathVariable int itemNum, Model model)
	{
		ItemVO item = itemSvc.detailItem(itemNum);
		model.addAttribute("item", item);
		return "item/updateItem";
	}
	
	@PostMapping("/update")
	@ResponseBody
	public Map<String, Object> updateResult(ItemVO item)
	{
		boolean updated = itemDAO.updateItem(item);
		Map<String, Object> map = new HashMap<>();
		map.put("updated", updated);
		return map;
	}
	
	@GetMapping("/delete/{itemNum}")
	@ResponseBody
	public Map<String, Object> deleteResult(@PathVariable int itemNum, HttpServletRequest request)
	{
		ItemVO item = itemSvc.detailItem(itemNum);
		List<ItemAttachVO> iattlist = item.getIattList();
		String filename = null;
		for(int i =0; i<iattlist.size();i++) {
			filename = iattlist.get(i).getItemAttachName();
		}
		boolean deleted = itemDAO.deleteItem(itemNum);
		Map<String, Object> map = new HashMap<>();
		map.put("deleted", deleted);
		if(deleted) {
			ServletContext context = request.getServletContext();
			String savePath = context.getRealPath("/items");
			File delFile = new File(savePath, filename);
			delFile.delete();
		}
		return map;
	}
	
}
