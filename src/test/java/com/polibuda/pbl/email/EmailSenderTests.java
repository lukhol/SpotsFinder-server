package com.polibuda.pbl.email;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=EmailSenderImpl.class, loader=AnnotationConfigContextLoader.class)
public class EmailSenderTests {
	
	@Autowired
	private EmailSenderImpl emailSender;
	
	@Before
	public void setUp(){
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		emailSender = new EmailSenderImpl(properties);
	}
	
	@Test(expected = NullPointerException.class)
	public void cannotCreateEmailSender_nullProperties(){
		emailSender = new EmailSenderImpl(null);
	}
	
	@Test
	public void canSendEmail(){
		boolean sendingEmailResult = emailSender.sendEmail("lukasz.holdrowicz@gmail.com", "Tests", "Main from unit test.");
		assert sendingEmailResult == true;
	}
	
	@Test
	public void cannotSendEmail_wrongProperties(){
		emailSender = new EmailSenderImpl(new Properties());
		boolean sendingEmailResult = emailSender.sendEmail("lukasz.holdrowicz@gmail.com", "Tests", "Main from unit test.");
		assert sendingEmailResult == false;
	}
}
