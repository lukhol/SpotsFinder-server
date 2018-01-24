package com.lukhol.spotsfinder.email;

import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lukhol.spotsfinder.email.EmailSenderImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailSenderTests {

	@Autowired
	private EmailSenderImpl emailSender;
	
	@Autowired
	@Qualifier("emailProperties")
	private Properties properties;
	
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
