package com.ezen.spring.project;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Data
public class ItemVO 
{
	private int itemNum;
	private String goods;
	private int price;
	private int purchaseCnt;
	private int cartCnt;
	private String category;
	private String explains;
	
	private List<ItemAttachVO> iattList;
}
