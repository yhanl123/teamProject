package com.ezen.spring.project;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/notice")
@SessionAttributes("userid")
public class NoticeController 
{
	@Autowired
	private NoticeDAO noticeDAO;
	
	@GetMapping("/")
	public String index()
	{
		return "notice/noticeIndex";
	}
	
	@GetMapping("/add")
	public String addNotice()
	{
		return "notice/addNotice";
	}
	
	@PostMapping("/add")
	@ResponseBody
	public Map<String, Object> addResult(NoticeVO n, @SessionAttribute(value="userid", required=false) String uid)
	{
		Map<String, Object> map = new HashMap<>();
		if(uid==null) {
			map.put("added", false);
			map.put("cause", "관리자만 작성 가능합니다");
			return map;
		}
		n.setNoticeAuthor(uid); //고정적 관리자가 들어가야 할 세션 설정 필요
		
		if(n.getNoticeAuthor().equals("admin")){
			boolean added = noticeDAO.addNotice(n);
			map.put("added", added);
			map.put("PKNum", n.getNoticeNum());
		}else {
			map.put("added", false);
			map.put("cause", "관리자만 작성 가능합니다");
		}
		return map;
	}
	
	@GetMapping("/list/page/{pn}")
	public String noticeList(@PathVariable int pn, Model model,
			@RequestParam(value="category", required = false) String category,
			@RequestParam(value="keyword", required = false) String keyword)
	{
		PageInfo<NoticeVO> pageInfo = null;
		
		if(category != null) {
			pageInfo = noticeDAO.searchByNotice(category, keyword, pn);
			
			model.addAttribute("category", category);
			model.addAttribute("keyword", keyword);
		}else {
			pageInfo = noticeDAO.noticeList(pn);
		}
		
		model.addAttribute("pageInfo", pageInfo);
		
		return "notice/noticeList";
	}
	
	@GetMapping("/get/{noticeNum}")
	public String getNotice(@PathVariable int noticeNum, Model model, @SessionAttribute(value="userid", required=false) String uid)
	{
		NoticeVO n = noticeDAO.getNotice(noticeNum);
		model.addAttribute("n", n);
		model.addAttribute("userid", uid);
		return "notice/getNotice";
	}
	
	@GetMapping("/edit/{noticeNum}")
	public String update(@PathVariable int noticeNum, Model model)
	{
		NoticeVO n = noticeDAO.getNotice(noticeNum);
		model.addAttribute("n", n);
		return "notice/noticeEditForm";
	}
	
	@PostMapping("/edit")
	@ResponseBody
	public Map<String, Object> updateResult(NoticeVO n)
	{
		boolean updated = noticeDAO.editNotice(n);
		Map<String, Object> map = new HashMap<>();
		map.put("updated", updated);
		return map;
	}
	
	@GetMapping("/delete/{noticeNum}")
	@ResponseBody
	public Map<String, Object> deleteResult(@PathVariable int noticeNum)
	{
		boolean deleted = noticeDAO.deleteNotice(noticeNum);
		Map<String, Object> map = new HashMap<>();
		map.put("deleted", deleted);
		return map;
	}
	
	@PostMapping("/search")
	public String search(@RequestParam("category") String category,
			 				@RequestParam("keyword") String keyword,
			 				Model model)
	{
		PageInfo<NoticeVO> pageInfo = noticeDAO.searchByNotice(category, keyword, 1);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("category", category); 
		model.addAttribute("keyword", keyword);
		return "notice/noticeList";
	}
}
