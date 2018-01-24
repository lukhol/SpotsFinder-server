package com.lukhol.spotsfinder.email;

public interface EmailSender {
	boolean sendEmail(String toAdress, String subject, String message);
}