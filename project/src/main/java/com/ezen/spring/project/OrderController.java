package com.ezen.spring.project;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/order")
@SessionAttributes("userid")
public class OrderController 
{
	@Autowired
	private OrderDAO orderDAO;
	
	@Autowired
	private CartDAO cartDAO;
	
	@Autowired
	private MemberDAO memberDAO;
	
	@Autowired
	private ItemSvc itemSvc;
	
	@PostMapping("/add")
	@ResponseBody
	public Map<String, Object> addOrder(@SessionAttribute(name="userid",required = false) String uid, @RequestBody List<Integer> cartNums)
	{
		boolean ordered = false;
		
		java.sql.Date date = new java.sql.Date(new Date().getTime());
		int paymentAmount = 0;
		for(int i=0;i<cartNums.size();i++) {
			CartVO c = cartDAO.getCartNum(cartNums.get(i));
			String goods = c.getGoods();
			int price = c.getPrice();
			int qty = c.getQty();
			paymentAmount += price * qty;
			
			OrderVO o = new OrderVO();
			o.setGoods(goods);
			o.setPrice(price);
			o.setQty(qty);
			o.setUser(uid);
			o.setOrderDate(date);
			
			boolean added = orderDAO.addOrder(o);
            if (added) {
                ordered = true;
                cartDAO.delete(c.getCartNum());
            }
		}
		int saveMoney = (int)(paymentAmount * 0.03);
		int point = 100;
		
		MemberVO orderUser = memberDAO.getUser(uid);
		int newSaveMoney = orderUser.getSaveMoney()+saveMoney;
		int newPoint = orderUser.getPoint()+point;
		
		orderUser.setSaveMoney(newSaveMoney);
		orderUser.setPoint(newPoint);
		
		memberDAO.updateUserSavingsAndPoints(orderUser);
		
		Map<String, Object> map = new HashMap<>();
		map.put("ordered", ordered);
		return map;
	}
	
	@PostMapping("/direct")
	@ResponseBody
	public Map<String,Object> derectOrder(@SessionAttribute(name="userid",required = false) String uid, @RequestParam("itemNum") int itemNum,@RequestParam("qty") int qty)
	{
		Map<String, Object> map = new HashMap<>();
		
		if(uid==null) { //로그인을 안했을 경우 add 불가능
			map.put("ordered",false);
			map.put("msg", "로그인을 해주세요.");
		}else {
			java.sql.Date date = new java.sql.Date(new Date().getTime());
			ItemVO item = itemSvc.detailItem(itemNum);
			String goods = item.getGoods();
			int price = item.getPrice();
			
			OrderVO o = new OrderVO();
			o.setGoods(goods);
			o.setPrice(price);
			o.setUser(uid);
			o.setQty(qty);
			o.setOrderDate(date);
			
			boolean ordered = orderDAO.addOrder(o);
			
			if(ordered) {
				int paymentAmount = price * qty;
				int saveMoney = (int)(paymentAmount * 0.03);
				int point = 100;
				
				MemberVO orderUser = memberDAO.getUser(uid);
				int newSaveMoney = orderUser.getSaveMoney()+saveMoney;
				int newPoint = orderUser.getPoint()+point;
				
				orderUser.setSaveMoney(newSaveMoney);
				orderUser.setPoint(newPoint);
				
				memberDAO.updateUserSavingsAndPoints(orderUser);
			}
			map.put("ordered", ordered);
		}
		return map;
	}
	
	@GetMapping("/list")
	public String orderList(Model model, @SessionAttribute(name="userid",required = false) String uid)
	{
		List<OrderVO> list = orderDAO.orderList(uid);
		model.addAttribute("list", list);
		model.addAttribute("uid", uid);
		return "itemOrder/orderList";
	}
}
