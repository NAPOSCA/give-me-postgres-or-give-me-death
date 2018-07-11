package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.wecancodeit.pantryplus2electricboogaloo.cart.Cart;
import org.wecancodeit.pantryplus2electricboogaloo.cart.CartRepository;
import org.wecancodeit.pantryplus2electricboogaloo.user.PantryUser;
import org.wecancodeit.pantryplus2electricboogaloo.user.UserRepository;

@Controller
public class EmailController extends LoginController {

	private static final String RECIPIENT = "bsfppantryplus@gmail.com";

	@Resource
	private JavaMailSender sender;

	@Resource
	private CartRepository cartRepo;

	@Resource
	private UserRepository userRepo;

	@Resource
	private EntityManager entityManager;

	@Resource
	private SpringTemplateEngine templateEngine;

	@RequestMapping("/email-failure")
	public String emailFailure(@RequestParam String error, Model model) {
		model.addAttribute("error", error);
		return "email-failure";
	}

	@Transactional
	@RequestMapping("/email")
	public String home(OAuth2AuthenticationToken token) {
		try {
			PantryUser user = resolveUser(token);
			Long cartId = user.getCart().getId();
			Cart cart = cartRepo.findById(cartId).orElse(null);
			String name;
			String firstName = user.getFirstName();
			String lastName = user.getLastName();
			name = firstName + " " + lastName;
			String subject = name + "s Order";
			Map<String, Object> message = cart.toModel();
			sendEmail(subject, message);
			return "redirect:/email-success.html";
		} catch (Exception exThrown) {
			return "redirect:/email-failure.html?error=" + exThrown;
		}
	}

	private void sendEmail(String subject, Map<String, Object> model) throws Exception {
		MimeMessage message = createMimeMessage();
		MimeMessageHelper helper = createMimeMessageHelper(message);

		String html = processModelintoHtml(model);

		setRecipient(helper);
		setBody(html, helper);
		setSubject(subject, helper);

		sendEmail(message);
	}

	private String processModelintoHtml(Map<String, Object> model) {
		Context context = createContext(model);
		String html = processContextIntoHtml(context);
		return html;
	}

	private String processContextIntoHtml(Context context) {
		String html = templateEngine.process("order", context);
		return html;
	}

	private Context createContext(Map<String, Object> model) {
		Context context = new Context();
		context.setVariables(model);
		return context;
	}

	private MimeMessageHelper createMimeMessageHelper(MimeMessage message) {
		MimeMessageHelper helper = new MimeMessageHelper(message);
		return helper;
	}

	void setRecipient(MimeMessageHelper helper) throws MessagingException {
		helper.setTo(RECIPIENT);
	}

	void setSubject(String subject, MimeMessageHelper helper) throws MessagingException {
		helper.setSubject(subject);
	}

	void setBody(String html, MimeMessageHelper helper) throws MessagingException {
		helper.setText(html, true);
	}

	void sendEmail(MimeMessage message) {
		sender.send(message);
	}

	public MimeMessage createMimeMessage() {
		MimeMessage message = sender.createMimeMessage();
		return message;
	}

}
