package com.tsspringexperience.utils;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.tsspringexperience.entities.EmailProps;

public class SendMail {
	
	public static boolean sendEmailWithHtmlContent(String htmlContent, String emailSubject, String[] toAddress_,
			String sender_email, String[] bccAddress_, EmailProps emailProps) {

		InternetAddress[] toAddress = null;
		InternetAddress[] bccAddress = null;

		toAddress = new InternetAddress[toAddress_.length];
		for (int i = 0; i < toAddress_.length; i++) {
			try {
				toAddress[i] = new InternetAddress(toAddress_[i]);
			} catch (AddressException e) {
				e.printStackTrace();
			}
		}
		if (bccAddress_ != null) {
			bccAddress = new InternetAddress[bccAddress_.length];
			for (int i = 0; i < bccAddress_.length; i++) {
				try {
					bccAddress[i] = new InternetAddress(bccAddress_[i]);
				} catch (AddressException e) {
					e.printStackTrace();
				}
			}
		} else {
			bccAddress = null;
		}

		return send(sender_email, toAddress, bccAddress, emailSubject, htmlContent, emailProps);
	}

	public static boolean send(String sender_email, Address[] toAddress, Address[] bccAddress, String emailSubject,
			String htmlContent, EmailProps emailProps) {

		try {
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", emailProps.getHost());
			props.put("mail.smtp.port", emailProps.getPort());
	
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(emailProps.getUserEmail(), emailProps.getPassword());
				}
			});
			
			Message message = new MimeMessage(session);

			if (sender_email == null) {
				sender_email = emailProps.getEmailSender();
			}

			message.setFrom(new InternetAddress(sender_email));
			message.setRecipients(RecipientType.TO, toAddress);
			if (bccAddress != null) {
				message.setRecipients(RecipientType.BCC, bccAddress);
			}
			message.setSubject(Credentials.isUat ? "UAT - " + emailSubject : emailSubject);

			message.setContent(htmlContent, "text/html");

			Transport.send(message);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
