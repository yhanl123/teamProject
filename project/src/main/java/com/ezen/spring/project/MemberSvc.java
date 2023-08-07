package com.ezen.spring.project;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.ServletContext;

@Service
public class MemberSvc 
{
	@Autowired
	private JavaMailSender sender;
	
	private String createRandomStr()
	{
		UUID randomUUID = UUID.randomUUID();
		return randomUUID.toString().replaceAll("-", "");
	}
	
	public boolean sendHTMLMessage(ServletContext ctx, String MemberEmail)
	{
		MimeMessage mimeMessage = sender.createMimeMessage();
		
		
		try {
			InternetAddress[] addressTo = new InternetAddress[1];
			addressTo[0] = new InternetAddress(MemberEmail);
			
			mimeMessage.setRecipients(Message.RecipientType.TO, addressTo);
			
			mimeMessage.setSubject("쇼핑몰 인증 메일");
			
			String authCode = createRandomStr();
	         
			Object mapObj = ctx.getAttribute("authMap");
	         
			if(mapObj==null) {
				Map<String, String> map = new HashMap<>();
				ctx.setAttribute("authMap", map);
				mapObj = map;
			}
	         
			Map<String, String> authMap = (Map) mapObj;
			authMap.put(MemberEmail, authCode);
	         
			ctx.setAttribute("authMap", authMap);
	         
			mimeMessage.setContent("<a href='http://localhost/member/auth/"+MemberEmail+"/"+authCode+"'>메일주소 인증</a>", "text/html;charset=utf-8");
	         
			sender.send(mimeMessage);
	         return true;
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return false;
	}
}
