package com.polibuda.pbl.email;

import org.springframework.stereotype.Component;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component
public class EmailSenderImpl implements EmailSender {

	private static final String FROM_MAIL = "nsai.activatorsender@gmail.com";
	private static final String PASSWORD = "nsaiactivator";
	
	public boolean sendEmail(String toAdress, String subject, String message) {
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
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