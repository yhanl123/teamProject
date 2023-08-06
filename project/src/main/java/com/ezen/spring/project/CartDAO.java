package com.ezen.spring.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class CartDAO 
{
	@Autowired
	private CartMapper cartMapper;
	
	@Autowired
	private ItemMapper itemMapper;
	
	@Transactional
	public boolean addCart(CartVO c)
	{
		itemMapper.cartCnt(c.getGoods());
		return cartMapper.addCart(c)>0;
	}
	
	public List<CartVO> cartList(String uid)
	{
		return cartMapper.cartList(uid);
	}
	
	public boolean updateCart(CartVO c)
	{
		return cartMapper.updateCart(c)>0;
	}
	
	public CartVO getCartNum(int cartNum) 
	{
		return cartMapper.getCartNum(cartNum);
	}
	
	public int delete(int cartNum) 
	{
		return cartMapper.delete(cartNum);
	}

	public int clear(String user) 
	{
		return cartMapper.clear(user);
	}

	public CartVO findCartByUserAndGoods(CartVO c) 
	{
		return cartMapper.findCartByUserAndGoods(c);
	}
}
