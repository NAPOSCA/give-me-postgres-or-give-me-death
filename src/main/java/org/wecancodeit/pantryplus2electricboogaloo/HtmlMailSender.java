package org.wecancodeit.pantryplus2electricboogaloo;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Service
public class HtmlMailSender {

	@Resource
	private JavaMailSender sender;

	@Resource
	private SpringTemplateEngine templateEngine;

	public void sendMail(String recipient, String subject, Model model, String template) throws MessagingException {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo(recipient);
		String html = processTemplateToHtmlWith(model, template);
		helper.setText(html, true);
		helper.setSubject(subject);
		sender.send(message);
	}

	private String processTemplateToHtmlWith(Model model, String template) {
		Context context = new Context();
		context.setVariables(model.asMap());
		return templateEngine.process(template, context);
	}

}
