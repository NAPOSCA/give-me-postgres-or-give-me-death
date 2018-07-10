package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import static org.mockito.Mockito.verify;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.Model;
import org.thymeleaf.spring5.SpringTemplateEngine;

public class EmailControllerMockTest {

	@InjectMocks
	private EmailController controller;

	@Mock
	private JavaMailSender sender;

	@Mock
	private SpringTemplateEngine templateEngine;

	@Mock
	private MimeMessageHelper helper;

	private Map<String, Object> cartModel;

	private MimeMessage message;

	@Mock
	Model springModel;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldSetRecipientEmailAddressMimeMessageHelper() throws MessagingException {
		String address = "bsfppantryplus@gmail.com";
		controller.setRecipient(helper);
		verify(helper).setTo(address);
	}

	@Test
	public void shouldSetSubjectOfMimeMessageHelper() throws MessagingException {
		String subject = "subject";
		controller.setSubject(subject, helper);
		verify(helper).setSubject(subject);
	}

}
