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

@Controller
@RequestMapping("/answer")
@SessionAttributes("userid")
public class AnswerController 
{
	@Autowired
	private AnswerDAO answerDAO;
	
	@Autowired
	private QuestionDAO questionDAO;

	@PostMapping("/add")
	@ResponseBody
	public Map<String, Object> addResult(AnswerVO a, @SessionAttribute(value="userid", required=false) String uid) {
	    Map<String, Object> map = new HashMap<>();

	    // 해당 질문에 대한 답변이 이미 있는지 확인
	    boolean isAnswerExist = answerDAO.checkAnswerExistsForQuestion(a.getPQuestionNum());

	    if (isAnswerExist) {
	        map.put("added", false); // 이미 답변이 등록되어 있으면 등록 불가
	    } else {
	        a.setAnswerAuthor(uid);
	        boolean added = answerDAO.addAnswer(a);
	        map.put("added", added); // 답변 등록 결과 확인
	    }

	    return map;
	}
	
	
	@GetMapping("/get/{questionNum}") // 질문 고유번호로 답변 가져오기
	@ResponseBody
	public Map<String, Object> getAnswer(@PathVariable int questionNum)
	{
		AnswerVO a = answerDAO.getAnswerByQuestion(questionNum);
		Map<String, Object> map = new HashMap<>();
		map.put("a", a);
		return map;
	}
	
	@GetMapping("/update/{answerNum}/{questionNum}")  //답변 수정하기 폼
	public String update(@PathVariable int answerNum, @PathVariable int questionNum, Model model)
	{
		QuestionVO q = questionDAO.getQuestion(questionNum); // 폼에 질문이 보여지기 위해서 내역 필요
		AnswerVO a = answerDAO.getAnswer(answerNum);
		model.addAttribute("a", a);
		model.addAttribute("q", q);
		return "question/answerEditForm";
	}
	
	@PostMapping("/update") //수정 결과 확인
	@ResponseBody
	public Map<String, Object> updateAnswer(AnswerVO a) 
	{	
		boolean updated = answerDAO.updateAnswer(a);
		Map<String, Object> map = new HashMap<>();
		map.put("updated", updated);
		return map;
	}

	@GetMapping("/delete/{answerNum}") //삭제
	@ResponseBody
	public Map<String, Object> deleteAnswer(@PathVariable int answerNum) 
	{
	    boolean deleted = answerDAO.deleteAnswer(answerNum);
	    Map<String, Object> map = new HashMap<>();
	    map.put("deleted", deleted);
	    return map;
	}
}
