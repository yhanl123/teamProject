package com.ezen.spring.project;

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
public class OrderVO 
{
	private int orderNum;
	private String goods;
	private String user;
	private int price;
	private int qty;
	private java.sql.Date orderDate;
}
