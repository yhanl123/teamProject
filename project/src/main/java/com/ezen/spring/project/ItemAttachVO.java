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
public class ItemAttachVO
{
	private int itemAttachNum;
	private String itemAttachName;
	private int itemParentsNum;
	private float itemFileSize;
	private String itemFileContentType;
}
