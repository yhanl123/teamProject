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

import com.github.pagehelper.PageInfo;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/review")
@SessionAttributes("userid")
public class ReviewController 
{
	@Autowired
	private ReviewDAO reviewDAO;
	
	@Autowired
	private ItemDAO itemDAO;
	
	@Autowired
	private ReviewSvc reviewSvc;
	
	@GetMapping("/")
	public String index()
	{
		return "review/reviewIndex";
	}
	
	@GetMapping("/add/{itemNum}")
	public String addReview(@PathVariable int itemNum, Model model)
	{
		String goods = null;
		List<Map<String, String>> list = itemDAO.detailItem(itemNum);
		for(int i=0 ; i<list.size();i++) {
			goods = list.get(i).get("goods");
		}
		model.addAttribute("goods", goods);
		model.addAttribute("itemNum", itemNum);
		return "review/addReviewForm";
	}
	
	@PostMapping("/add")  //리뷰 추가
	@ResponseBody
	public Map<String, Object> addResult(ReviewVO r, @RequestParam("files")MultipartFile[] mfiles,
			HttpServletRequest request, @SessionAttribute(name="userid",required = false) String uid)
	{
		ServletContext context = request.getServletContext();
		String savePath = context.getRealPath("/reviewPhoto");
		
		List<ReviewAttachVO> rattList = new ArrayList<>();
		
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
				
				ReviewAttachVO ratt = new ReviewAttachVO();
				ratt.setReviewAttachName(originName);
				ratt.setReviewFileSize(fSize/1024);
				ratt.setReviewFileContentType(cType);
				
				rattList.add(ratt);
			}
			
			r.setRattList(rattList);
			r.setReviewAuthor(uid);
			boolean added = reviewDAO.addReview(r);
			Map<String, Object> map = new HashMap<>();
			map.put("added", added);
			map.put("PKNum", r.getReviewNum());
			
			return map;
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		Map<String, Object> map = new HashMap<>();
		map.put("added", false);
		return map;
	}
	
	@GetMapping("/list/{itemNum}/page/{pn}")  //리뷰리스트
	public String reviewList(@PathVariable int itemNum, @PathVariable int pn, Model model)
	{
		PageInfo<Map> pageInfo = reviewDAO.reviewList(itemNum,pn);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("itemNum", itemNum);
		return "item/reviewList";
	}
	
	@GetMapping("/get/{reviewNum}")  //리뷰 상세보기
	public String detailReview(@PathVariable int reviewNum, Model model, @SessionAttribute(name="userid",required = false) String uid)
	{
		ReviewVO r = reviewSvc.detailReview(reviewNum);
		model.addAttribute("r", r);
		model.addAttribute("uid", uid);
		return "review/detailReview";
	}
	
	@GetMapping("/remove/{reviewAttachNum}") //리뷰 첨부파일 삭제
	@ResponseBody
	public Map<String,Object> attachRemove(@PathVariable int reviewAttachNum, HttpServletRequest request)
	{
		boolean removed = reviewSvc.removeAttach(reviewAttachNum, request);
		Map<String, Object> map = new HashMap<>();
		map.put("removed", removed);
		return map;
	}
	
	@PostMapping("/addattach") //첨부파일 추가시
	@ResponseBody
	public Map<String,Object> addAttachReview(@RequestParam("files")MultipartFile[] mfiles, HttpServletRequest request, @RequestParam("reviewParentsNum") int reviewParentsNum)
	{
		ServletContext context = request.getServletContext();
		String savePath = context.getRealPath("/reviewPhoto");
		
		List<ReviewAttachVO> rattList = new ArrayList<>();
		
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
				
				ReviewAttachVO ratt = new ReviewAttachVO();
				ratt.setReviewAttachName(originName);
				ratt.setReviewFileSize(fSize/1024);
				ratt.setReviewFileContentType(cType);
				ratt.setReviewAttachParentsNum(reviewParentsNum);
				
				rattList.add(ratt);
			}
			boolean added = reviewDAO.addAttachReview(rattList);
			Map<String, Object> map = new HashMap<>();
			map.put("added", added);
			return map;
		}catch(Exception ex) {
				ex.printStackTrace();
		}
		Map<String, Object> map = new HashMap<>();
		map.put("added", false);
		return map;
	}
	
	@GetMapping("/update/{reviewNum}") //수정폼
	public String updateReview(@PathVariable int reviewNum, Model model)
	{
		ReviewVO r = reviewSvc.detailReview(reviewNum);
		model.addAttribute("r", r);
		return "review/reviewUpdateForm";
	}
	
	@PostMapping("/update")  //수정
	@ResponseBody
	public Map<String,Object> updateResult(ReviewVO r)
	{
		boolean updated = reviewDAO.updateReview(r);
		Map<String,Object> map = new HashMap<>();
		map.put("updated", updated);
		return map;
	}
	
	@GetMapping("/delete/{reviewNum}")  //삭제
	@ResponseBody
	public Map<String,Object> deleteResult(@PathVariable int reviewNum)
	{
		boolean deleted = reviewDAO.deleteReview(reviewNum);
		Map<String,Object> map = new HashMap<>();
		map.put("deleted", deleted);
		return map;
	}
	
	@GetMapping("/like/{reviewNum}")  //좋아요 버튼
	@ResponseBody
	public Map<String,Object> likeCnt(@PathVariable int reviewNum, @SessionAttribute(name="userid",required = false) String uid)
	{
		boolean likeUpdated = reviewDAO.likeCnt(reviewNum);
		Map<String,Object> map = new HashMap<>();
		map.put("likeUpdated", likeUpdated);
		map.put("userid", uid);
		return map;
	}
}
