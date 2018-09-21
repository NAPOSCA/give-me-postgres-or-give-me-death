package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.wecancodeit.pantryplus2electricboogaloo.HtmlMailSender;
import org.wecancodeit.pantryplus2electricboogaloo.LoginService;
import org.wecancodeit.pantryplus2electricboogaloo.user.PantryUser;

@Controller
public class EmailController {

	private static final String RECIPIENT = "bsfppantryplus@gmail.com";

	@Resource
	private LoginService loginService;

	@Resource
	private HtmlMailSender sender;

	@RequestMapping("/email")
	public String sendEmail(OAuth2User googleId, Model model) throws MessagingException {
		PantryUser user = loginService.resolveUser(googleId);
		model.addAttribute("user", user);
		model.addAttribute("cart", user.getCart());
		String subject = String.format("%s %s's Order", user.getFirstName(), user.getLastName());
		sender.sendMail(RECIPIENT, subject, model, "order");
		return "redirect:/email-success.html";
	}

	@ExceptionHandler({ MessagingException.class })
	public ModelAndView handleEmailException(MessagingException ex) {
		ModelAndView mav = new ModelAndView("email-failure");
		mav.addObject("error", ex);
		return mav;
	}

}
