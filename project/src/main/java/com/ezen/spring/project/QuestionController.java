package com.ezen.spring.project;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/question")
@SessionAttributes("userid")
public class QuestionController 
{
	@Autowired
	private QuestionDAO questionDAO;
	
	@Autowired
	private AnswerDAO answerDAO;
	
	@GetMapping("/")
	public String index()
	{
		return "question/questionIndex";
	}
	
	@GetMapping("/add")
	public String addQuestion(@SessionAttribute(name="userid",required = false) String uid, Model model)
	{
		if(uid!=null) {
			return "question/addQuestion";
		}else {
			return "redirect:/member/login";
		}
	}
	
	@PostMapping("/add")
	@ResponseBody
	public Map<String, Object> addResult(@SessionAttribute(name="userid",required = false) String uid, QuestionVO q)
	{
		Map<String, Object> map = new HashMap<>();
		q.setQuestionAuthor(uid);
		boolean added = questionDAO.addQuestion(q);
		map.put("added", added);
		map.put("PKNum", q.getQuestionNum());
		return map;
	}
	
	@GetMapping("/get/{questionNum}")
	public String getQuestion(@PathVariable int questionNum, Model model, @SessionAttribute(name="userid", required = false) String uid)
	{
		QuestionVO q = questionDAO.getQuestion(questionNum);
		AnswerVO a = answerDAO.getAnswerByQuestion(questionNum);
		model.addAttribute("q", q);
		model.addAttribute("a", a);
		model.addAttribute("userid", uid);
		return "question/detailQuestion";
	}
	
	@GetMapping("/list")
	public String questionList(Model model, @SessionAttribute(name="userid", required = false) String uid)
	{
		List<QuestionVO> list = questionDAO.getList();
		Map<Integer, Boolean> answerStatusMap = new HashMap<>();
		
	    for (QuestionVO q : list) { //질문 리스트 중에 한 컬럼을 만들어서 답변의 존재 유무표시(답변 완료/ 답변 예정)
	        boolean hasAnswer = answerDAO.checkAnswerExistsForQuestion(q.getQuestionNum());
	        answerStatusMap.put(q.getQuestionNum(), hasAnswer);
	    }
		
		model.addAttribute("list", list);
		model.addAttribute("userid", uid);
		model.addAttribute("answerStatusMap", answerStatusMap); //맵에 담아 보여주기
		
		return "question/questionList";
	}
	
	@GetMapping("/update/{questionNum}")
	public String update(@PathVariable int questionNum, Model model)
	{
		QuestionVO q = questionDAO.getQuestion(questionNum);
		model.addAttribute("q", q);
		return "question/updateQuestion";
	}
	
	@PostMapping("/update")
	@ResponseBody
	public Map<String, Object> updateResult(QuestionVO q)
	{
		boolean updated = questionDAO.updateQuestion(q);
		Map<String, Object> map = new HashMap<>();
		map.put("updated", updated);
		return map;
	}
	
	@GetMapping("/delete/{questionNum}")
	@ResponseBody
	public Map<String, Object> deleteResult(@PathVariable int questionNum)
	{
		boolean deleted = questionDAO.deleteQuestion(questionNum);
		Map<String, Object> map = new HashMap<>();
		map.put("deleted", deleted);
		return map;
	}
}
