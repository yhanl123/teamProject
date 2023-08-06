package com.ezen.spring.project;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface CartMapper 
{
	public int addCart(CartVO c);
	
	public List<CartVO> cartList(String uid);
	
	public int updateCart(CartVO c);
	
	public CartVO getCartNum(int cartNum);
	
	public int delete(int cartNum);
	
	public int clear(String user);

	public CartVO findCartByUserAndGoods(CartVO c);
}
