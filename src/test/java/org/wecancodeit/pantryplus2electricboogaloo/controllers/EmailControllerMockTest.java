package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.mail.MessagingException;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.wecancodeit.pantryplus2electricboogaloo.HtmlMailSender;
import org.wecancodeit.pantryplus2electricboogaloo.LoginService;
import org.wecancodeit.pantryplus2electricboogaloo.cart.Cart;
import org.wecancodeit.pantryplus2electricboogaloo.user.PantryUser;

public class EmailControllerMockTest {

	@InjectMocks
	private EmailController underTest;

	@Mock
	private LoginService loginService;

	@Mock
	private HtmlMailSender sender;

	@Mock
	private OAuth2User googleId;

	@Mock
	private Model model;

	String recipient = "bsfppantryplus@gmail.com";
	String template = "order";

	@Mock
	private PantryUser user;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		when(loginService.resolveUser(googleId)).thenReturn(user);
	}

	@Test
	public void shouldRedirectToSuccessPageAfterEmailIsSent() throws MessagingException {
		String redirect = underTest.sendEmail(googleId, model);
		assertThat(redirect, Matchers.is("redirect:/email-success.html"));
	}

	@Test
	public void shouldSendEmailWithCorrectData() throws MessagingException {
		String firstName = "Scooby";
		String lastName = "Doo";
		when(user.getFirstName()).thenReturn(firstName);
		when(user.getLastName()).thenReturn(lastName);
		underTest.sendEmail(googleId, model);
		verify(sender).sendMail(recipient, firstName + " " + lastName + "'s Order", model, template);
	}

	@Test
	public void shouldSendEmailWithMyNameOnTheOrder() throws MessagingException {
		String firstName = "Alex";
		String lastName = "Malcolm";
		when(user.getFirstName()).thenReturn(firstName);
		when(user.getLastName()).thenReturn(lastName);
		underTest.sendEmail(googleId, model);
		verify(sender).sendMail(recipient, firstName + " " + lastName + "'s Order", model, template);
	}

	@Test
	public void shouldAddUserToModelBeforeSendingEmail() throws MessagingException {
		underTest.sendEmail(googleId, model);
		InOrder inOrder = inOrder(model, sender);
		inOrder.verify(model).addAttribute("user", user);
		inOrder.verify(sender).sendMail(recipient, "", model, template);
	}

	@Test
	public void shouldAddCartToModelBeforeSendingEmail() throws MessagingException {
		Cart cart = new Cart(user);
		when(user.getCart()).thenReturn(cart);
		underTest.sendEmail(googleId, model);
		InOrder inOrder = inOrder(model, sender);
		inOrder.verify(model).addAttribute("cart", cart);
		inOrder.verify(sender).sendMail(recipient, "", model, template);
	}

}
