package com.jira.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;


@Component
public class EmailUtil {
	private static final String SENDER_EMAIL = "ittalentsjira@gmail.com";
	private static final String SENDER_PASS = "ittalentsjira123";
	public static final String SUBJECT_TEXT_FORGOTTEN_PASSWORD = "Jira FORGOTTEN PASSWORD";
	public static final String FORGOTTEN_PASSWORD_EMAIL_TEXT = "To %s reset your password click the link below change password http://localhost:8080/Jira/resetPassword/%s";

	public void sendEmail(String receiverEmail, String subjectText, String msg) {

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

	
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASS);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(SENDER_EMAIL));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmail));
			message.setSubject(subjectText);
			message.setText(msg);

			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
