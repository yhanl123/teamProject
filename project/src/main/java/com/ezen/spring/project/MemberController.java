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
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/member")
@SessionAttributes("userid")
public class MemberController 
{
	@Autowired
	private MemberDAO memberDAO;
	
	@GetMapping("/")
	public String index(@SessionAttribute(value="userid", required=false) String uid, Model model)
	{
		model.addAttribute("userid", uid);
		return "member/indexPage";
	}
	
	@GetMapping("/add")
	public String add()
	{
		return "member/addMember";
	}
	
	@PostMapping("/add")
	@ResponseBody
	public Map<String, Object> addResult(MemberVO m)
	{
		boolean added = memberDAO.addMember(m);
		Map<String, Object> map = new HashMap<>();
		map.put("added", added);
		return map;
	}
	
	@PostMapping("/checkDuplicate")
	@ResponseBody
	public Map<String, Object> checkDuplicate(@RequestParam String memberID) 
	{
		boolean duplicated = memberDAO.checkDuplicate(memberID);
		Map<String, Object> map = new HashMap<>();
		map.put("duplicated", duplicated);
		return map;
	}
	
	@GetMapping("/login")
	public String login()
	{
		return "member/MemberLoginForm";
	}
	
	@PostMapping("/login")
	@ResponseBody
	public Map<String, Object> loginResult(MemberVO m, Model model)
	{
		boolean login = memberDAO.login(m.getMemberID(), m.getMemberPwd());
		Map<String, Object> map = new HashMap<>();
		map.put("login", login);
		if(login) {
			model.addAttribute("userid", m.getMemberID()); //Session에 저장하는 효과
		}
		return map;
	}
	
	@GetMapping("/logout")
	@ResponseBody
	public Map<String, Boolean> logout(SessionStatus status)
	{
		Map<String, Boolean> map = new HashMap<>();
		status.setComplete();
		map.put("ok", true); 
		
		return map;
	}
	
	@GetMapping("/findid")
	public String findId()
	{
		return "member/findIdForm";
	}
	
	@GetMapping("/findpwd")
	public String findPwd()
	{
		return "member/findPwdForm";
	}
	
	@PostMapping("/findid")
	public String findIdResult(MemberVO m, Model model)
	{
		MemberVO findId = memberDAO.findId(m.getMemberName(), m.getMemberEmail());
		
		if(findId !=null) {
			model.addAttribute("findUser", findId.getMemberID());
		}else {
			model.addAttribute("findUser", "일치하는 사용자를 찾을 수 없습니다.");
		}
		return "member/findUser";
	}
	
	@PostMapping("/findpwd")
	public String findPwdResult(MemberVO m, Model model)
	{
		MemberVO findPwd = memberDAO.findPwd(m.getMemberName(), m.getMemberID());
		
		if(findPwd !=null) {
			model.addAttribute("findUser", findPwd.getMemberPwd());
		}else {
			model.addAttribute("findUser", "일치하는 사용자를 찾을 수 없습니다.");
		}
		return "member/findUser";
	}
	
	@GetMapping("/get/{memberNum}")
	public String getMember(@PathVariable int memberNum, Model model)
	{
		MemberVO m = memberDAO.getMember(memberNum);
		model.addAttribute("m", m);
		return "member/detailMember";
	}
	
	@GetMapping("/getbyid/{memberID}")
	public String getMemberByID(@PathVariable String memberID, Model model)
	{
		MemberVO m = memberDAO.getMemberByID(memberID);
		model.addAttribute("m", m);
		return "member/detailMember";
	}
	
	@GetMapping("/update/{memberNum}")
	public String update(@PathVariable int memberNum, Model model)
	{
		MemberVO m = memberDAO.getMember(memberNum);
		model.addAttribute("m", m);
		return "member/updateMemberForm";
	}
	
	@PostMapping("/update")
	@ResponseBody
	public Map<String, Object> updateResult(MemberVO m)
	{
		boolean updated = memberDAO.update(m);
		Map<String, Object> map = new HashMap<>();
		map.put("updated", updated);
		return map;
	}
	
	@GetMapping("/delete/{memberNum}")
	@ResponseBody
	public Map<String, Object> deleteResult(@PathVariable int memberNum, SessionStatus status)
	{
		boolean deleted = memberDAO.deleteMem(memberNum);
		status.setComplete();
		Map<String, Object> map = new HashMap<>();
		map.put("deleted", deleted);
		return map;
	}
}
