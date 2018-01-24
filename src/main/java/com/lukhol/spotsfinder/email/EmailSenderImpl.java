package com.lukhol.spotsfinder.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import lombok.NonNull;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component
public class EmailSenderImpl implements EmailSender {

	private static final String FROM_MAIL = "nsai.activatorsender@gmail.com";
	private static final String PASSWORD = "nsaiactivator";
	private final Properties properties;
	
	@Autowired
	public EmailSenderImpl(@Qualifier("emailProperties") @NonNull Properties properties){
		this.properties = properties;
	}
	
	public boolean sendEmail(String toAdress, String subject, String message) {
		
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(FROM_MAIL, PASSWORD);
			}
		});
		
		try {
			Message mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress(FROM_MAIL));
			
			mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAdress));
			mimeMessage.setSubject(subject);
			mimeMessage.setText(message);
			
			Transport.send(mimeMessage);
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
	}
}