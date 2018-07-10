package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import javax.mail.MessagingException;

import org.springframework.mail.javamail.MimeMessageHelper;

public class EmailController {

	private static final String RECIPIENT = "bsfppantryplus@gmail.com";

	void setRecipient(MimeMessageHelper helper) throws MessagingException {
		helper.setTo(RECIPIENT);
	}

	void setSubject(String subject, MimeMessageHelper helper) throws MessagingException {
		helper.setSubject(subject);

	}

	void setBody(String html, MimeMessageHelper helper) throws MessagingException {
		helper.setText(html, true);

	}

}
