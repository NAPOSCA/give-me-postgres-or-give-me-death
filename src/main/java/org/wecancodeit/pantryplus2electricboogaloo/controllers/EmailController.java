package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmailController {

	private static final String RECIPIENT = "bsfppantryplus@gmail.com";

	@Resource
	private JavaMailSender sender;

	void setRecipient(MimeMessageHelper helper) throws MessagingException {
		helper.setTo(RECIPIENT);
	}

	void setSubject(String subject, MimeMessageHelper helper) throws MessagingException {
		helper.setSubject(subject);

	}

	void setBody(String html, MimeMessageHelper helper) throws MessagingException {
		helper.setText(html, true);

	}

	@RequestMapping("/email-failure")
	public String emailFailure(@RequestParam String error, Model model) {
		model.addAttribute("error", error);
		return "email-failure";
	}

	void sendEmail(MimeMessage message) {
		sender.send(message);
	}

	public MimeMessage createMimeMessage() {
		MimeMessage message = sender.createMimeMessage();
		return message;
	}

}
