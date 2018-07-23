package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.wecancodeit.pantryplus2electricboogaloo.LoginService;
import org.wecancodeit.pantryplus2electricboogaloo.cart.Cart;
import org.wecancodeit.pantryplus2electricboogaloo.category.Category;
import org.wecancodeit.pantryplus2electricboogaloo.category.CategoryRepository;
import org.wecancodeit.pantryplus2electricboogaloo.lineitem.CountedLineItem;
import org.wecancodeit.pantryplus2electricboogaloo.lineitem.LineItem;
import org.wecancodeit.pantryplus2electricboogaloo.user.PantryUser;
import org.wecancodeit.pantryplus2electricboogaloo.user.UserRepository;

public class PantryControllerMockTest {

	@InjectMocks
	private PantryController underTest;

	@Mock
	private Category category;

	@Mock
	private Category anotherCategory;

	@Mock
	private CategoryRepository categoryRepo;

	@Mock
	private Model model;

	@Mock
	private OAuth2User googleId;

	@Mock
	private UserRepository userRepo;

	@Mock
	private PantryUser user;

	@Mock
	private Cart cart;

	@Mock
	private LineItem lineItem;

	@Mock
	private LineItem anotherLineItem;

	@Mock
	private CountedLineItem countedLineItem;

	@Mock
	private CountedLineItem anotherCountedLineItem;
	
	@Mock
	private EntityManager entityManager;
	
	@Mock
	private LoginService loginService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		when(user.getCart()).thenReturn(cart);
		when(loginService.resolveUser(googleId)).thenReturn(user);
	}

	@Test
	public void shouldHaveDisplayUserFormReturnUserForm() {
		String templateName = "user-form";
		String actual = underTest.displayUserForm(model, googleId);
		assertThat(actual, is(templateName));
	}

	@Test
	public void shouldHaveDisplayShoppingReturnShoppingWhenUserIsValid() {
		when(user.isValid()).thenReturn(true);
		String templateName = "shopping";
		String actual = underTest.displayShopping(model, googleId);
		assertThat(actual, is(templateName));
	}

	@Test
	public void shouldHaveDisplayCartReturnCart() {
		String templateName = "cart";
		when(user.getCart()).thenReturn(cart);
		Set<LineItem> lineItems = new HashSet<>();
		lineItems.addAll(asList(lineItem, anotherLineItem, countedLineItem));
		when(cart.getAllLineItems()).thenReturn(lineItems);
		String actual = underTest.displayCart(model, googleId);
		assertThat(actual, is(templateName));
	}

	@Test
	public void shouldHaveDisplayShoppingAddCategoriesToModel() {
		Iterable<Category> categories = asList(category, anotherCategory);
		when(categoryRepo.findAll()).thenReturn(categories);
		underTest.displayShopping(model, googleId);
		verify(model).addAttribute("categories", categories);
	}

	@Test
	public void shouldAttachCartToModelWhenDisplayingShoppingAndWhenUserIsValid() {
		when(user.isValid()).thenReturn(true);
		Iterable<Category> categories = asList(category, anotherCategory);
		when(categoryRepo.findAll()).thenReturn(categories);
		when(user.getCart()).thenReturn(cart);

		underTest.displayShopping(model, googleId);
		verify(model).addAttribute("cart", cart);
	}

	@Test
	public void shouldReturnTemplateNameForAboutUs() {
		String templateName = "about-us";
		String actual = underTest.displayAboutUs();
		assertThat(actual, is(templateName));
	}

	@Test
	public void shouldHaveDisplayCartAttachLineItemsAndNoCountedLineItems() {
		HashSet<LineItem> lineItems = new HashSet<>(asList(lineItem, anotherLineItem));
		when(cart.getLineItems()).thenReturn(lineItems);
		underTest.displayCart(model, googleId);
		verify(model).addAttribute("lineItems", lineItems);
	}

	@Test
	public void shouldHaveDisplayCartAttachCountedLineItemsAndNoLineItems() {
		HashSet<CountedLineItem> countedLineItems = new HashSet<>(asList(countedLineItem, anotherCountedLineItem));
		when(cart.getCountedLineItems()).thenReturn(countedLineItems);
		underTest.displayCart(model, googleId);
		verify(model).addAttribute("countedLineItems", countedLineItems);
	}

	@Test
	public void shouldHaveDisplayUserFormAttachUserToModel() {
		underTest.displayUserForm(model, googleId);
		verify(model).addAttribute("user", user);
	}

	@Test
	public void shouldHaveDisplayCartAttachCartToModel() {
		underTest.displayCart(model, googleId);
		verify(model).addAttribute("cart", cart);
	}

	@Test
	public void shouldHaveDisplayWelcomePageReturnWelcomeWhenUserIsValid() {
		when(user.isValid()).thenReturn(true);
		String templateName = underTest.displayWelcomeView(model, googleId);
		assertThat(templateName, is("welcome"));
	}

	@Test
	public void shouldAttachAuthenticatedAsTrueToModelIfUserIsSignedIn() {
		underTest.displayWelcomeView(model, googleId);
		verify(model).addAttribute("authenticated", true);
	}

	@Test
	public void shouldAttachAuthenticatedAsFalseToModelIfUserIsNotSignedIn() {
		googleId = null;
		underTest.displayWelcomeView(model, googleId);
		verify(model).addAttribute("authenticated", false);
	}

	@Test
	public void shouldAttachUserIfAuthenticatedAndUserIsValid() {
		when(user.isValid()).thenReturn(true);
		underTest.displayWelcomeView(model, googleId);
		verify(model).addAttribute("user", user);
	}

	@Test
	public void shouldRedirectFromTheWelcomeViewToTheSettingsPageIfUserIsNotValid() {
		when(user.isValid()).thenReturn(false);
		String templateName = underTest.displayWelcomeView(model, googleId);
		assertThat(templateName, is("redirect:/settings"));
	}
	
	@Test
	public void shouldRedirectFromTheShoppingViewToTheSettingsPageIfUserIsNotValid() {
		when(user.isValid()).thenReturn(false);
		String templateName = underTest.displayShopping(model, googleId);
		assertThat(templateName, is("redirect:/settings"));
	}

}
