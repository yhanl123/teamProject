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

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/cart")
@SessionAttributes("userid")
@Slf4j
public class CartController 
{
	@Autowired
	private CartDAO cartDAO;
	
	@Autowired
	private ItemSvc itemSvc;
	
	@PostMapping("/add")
	@ResponseBody
	public Map<String, Object> add(@SessionAttribute(name="userid",required = false) String uid,@RequestParam("itemNum") int itemNum,@RequestParam("qty") int qty)
	{
		Map<String, Object> map = new HashMap<>();
		if(uid==null) { //로그인을 안했을 경우 add 불가능
			map.put("added",false);
			map.put("msg", "로그인을 해주세요.");
		}else {
			ItemVO item = itemSvc.detailItem(itemNum);
			String goods = item.getGoods();
			int price = item.getPrice();
			
			CartVO cart = new CartVO();
	        cart.setUser(uid);
	        cart.setGoods(goods);
			
			CartVO existingCart = cartDAO.findCartByUserAndGoods(cart);
	        if (existingCart != null) {
	            // 이미 장바구니에 같은 아이템이 있으면 수량만 증가
	        	int existingQty = existingCart.getQty();
	            existingCart.setQty(existingQty + qty);
	            boolean updated = cartDAO.updateCart(existingCart);
	            map.put("added", updated);
	        }else {
				CartVO c = new CartVO();
				c.setUser(uid);
				c.setGoods(goods);
				c.setPrice(price);
				c.setQty(qty);
				boolean added = cartDAO.addCart(c);
				map.put("added", added);
	        }
		}
		return map;
	}
	
	@GetMapping("/list")  
	public String cartList(Model model, @SessionAttribute(name="userid",required = false) String uid)
	{
		List<CartVO> list = cartDAO.cartList(uid);
		model.addAttribute("list", list);
		model.addAttribute("uid", uid);
		return "itemCart/cartList";
	}
	 
	@PostMapping("/update")  //수정 결과 메시지
	@ResponseBody
	public Map<String, Object> updateResult(@RequestParam int cartNum, @RequestParam int qty)
	{
		CartVO c = new CartVO();
		c.setCartNum(cartNum);
		c.setQty(qty);
		
		boolean updated = cartDAO.updateCart(c);
		Map<String, Object> map = new HashMap<>();
		map.put("updated", updated);
		return map;
	}
	
	@PostMapping("/delete")  //삭제
	@ResponseBody
	public Map<String, Object> delete(@SessionAttribute(name="userid",required = false) String uid,@RequestBody List<Integer> cartNums)
	{
		Map<String, Object> map = new HashMap<>();
		List<CartVO> cart = cartDAO.cartList(uid);

		if(cart==null) {
			map.put("deleted", false);
		}else {
			for(int i=0;i<cartNums.size();i++) {
				cartDAO.delete(cartNums.get(i));            
			} 
			map.put("deleted", true);
		}
		return map;
	}
	   
	@GetMapping("/clear")  //카트 전부 비우기
	@ResponseBody
	public Map<String, Object> clear(@SessionAttribute(name="userid",required = false) String uid)
	{
		int cleared = cartDAO.clear(uid);
		
		Map<String, Object> map = new HashMap<>();
		map.put("cleared", cleared>0);
		map.put("cleared", true);
		return map;
	}

}
