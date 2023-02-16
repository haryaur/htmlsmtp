package com.example.demo;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Header;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MailController {
	
	@Autowired
	private JavaMailSender mailSender;


	
	@GetMapping("/contact")
	public String Method(){
	return "contact";
	}
	
	@PostMapping("/contact")
	public String SunbmitMethod(HttpServletRequest request)
	throws MessagingException, UnsupportedEncodingException {
		
	String fullname = request.getParameter("fullname");
	String email = request.getParameter("email");
	String subject = request.getParameter("subject");
	String content = request.getParameter("content");
	
	MimeMessage message = mailSender.createMimeMessage();
	MimeMessageHelper helper = new MimeMessageHelper(message, true);
	
	String mailSubject = fullname + " has sent a message" + "\n";
	String mailContent =  "<b>Sender Name: </b>" +fullname +"\n<br>";
	mailContent += "<b>Email: </b>" + email+ "\n<br>";
	mailContent += "<b>Subject: </b>" + subject + "\n<br>";
	mailContent += "<b><h4>Content: </h4></b>" + content + "\n<br>";
	
	 /* using the setTo() to your email and setting setfrom() to your email, fullname(above)
	  *  you can use it as Contact us Form
	  * 
	  * setting the setTo() to email(above) and set From() to your email
	  * you can use it to send message outward to another recipient
	  * 
	  * */
	
	helper.setFrom( email, fullname);
	helper.setTo("xyz@gmail.com");
	helper.setSubject(mailSubject);
	helper.setText(mailContent, true );
	
	mailSender.send(message);

	return "message";
	}
}
