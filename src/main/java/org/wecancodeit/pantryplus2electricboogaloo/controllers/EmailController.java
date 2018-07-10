package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import javax.mail.MessagingException;

import org.springframework.mail.javamail.MimeMessageHelper;

public class EmailController {

	private static final String RECIPIENT = "bsfppantryplus@gmail.com";

	void setRecipient(MimeMessageHelper helper) throws MessagingException {
		helper.setTo(RECIPIENT);
	}

}
