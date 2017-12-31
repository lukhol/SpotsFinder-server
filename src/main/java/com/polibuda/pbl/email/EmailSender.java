package com.polibuda.pbl.email;

public interface EmailSender {
	boolean sendEmail(String toAdress, String subject, String message);
}