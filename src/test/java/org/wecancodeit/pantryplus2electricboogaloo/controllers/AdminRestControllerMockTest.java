package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.wecancodeit.pantryplus2electricboogaloo.LoginService;
import org.wecancodeit.pantryplus2electricboogaloo.category.Category;
import org.wecancodeit.pantryplus2electricboogaloo.category.CategoryRepository;
import org.wecancodeit.pantryplus2electricboogaloo.currency.CurrencyRepository;
import org.wecancodeit.pantryplus2electricboogaloo.product.ProductRepository;

public class AdminRestControllerMockTest {

	@InjectMocks
	private AdminRestController underTest;

	@Mock
	private CurrencyRepository currencyRepo;

	@Mock
	private CategoryRepository categoryRepo;

	@Mock
	private ProductRepository productRepo;

	@Mock
	private LoginService loginService;

	@Mock
	private OAuth2User googleId;

	@Mock
	private Category category;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		when(loginService.isAdmin(googleId)).thenReturn(true);
	}

	@Test
	public void shouldDeleteFirstCurrency() {
		long currencyId = 1L;
		underTest.receiveDeleteOnCurrency(googleId, currencyId);
		verify(currencyRepo).deleteById(currencyId);
	}

	@Test
	public void shouldDeleteSecondCurrency() {
		long currencyId = 2L;
		underTest.receiveDeleteOnCurrency(googleId, currencyId);
		verify(currencyRepo).deleteById(currencyId);
	}

	@Test
	public void shouldDeleteFirstCategory() {
		long categoryId = 1L;
		underTest.receiveDeleteOnCategory(googleId, categoryId);
		verify(categoryRepo).deleteById(categoryId);
	}

	@Test
	public void shouldDeleteSecondCategory() {
		long categoryId = 2L;
		underTest.receiveDeleteOnCategory(googleId, categoryId);
		verify(categoryRepo).deleteById(categoryId);
	}

	@Test
	public void shouldDeleteFirstProduct() {
		long productId = 1L;
		underTest.receiveDeleteOnProduct(googleId, productId);
		verify(productRepo).deleteById(productId);
	}

	@Test
	public void shouldDeleteSecondProduct() {
		long productId = 2L;
		underTest.receiveDeleteOnProduct(googleId, productId);
		verify(productRepo).deleteById(productId);
	}

	@Test
	public void shouldNotDeleteCurrencyIfNotAdmin() {
		when(loginService.isAdmin(googleId)).thenReturn(false);
		long currencyId = 1L;
		underTest.receiveDeleteOnCurrency(googleId, currencyId);
		verify(currencyRepo, never()).deleteById(currencyId);
	}

	@Test
	public void shouldNotDeleteCategoryIfNotAdmin() {
		when(loginService.isAdmin(googleId)).thenReturn(false);
		long categoryId = 1L;
		underTest.receiveDeleteOnCategory(googleId, categoryId);
		verify(categoryRepo, never()).deleteById(categoryId);
	}

	@Test
	public void shouldNotDeleteProductIfNotAdmin() {
		when(loginService.isAdmin(googleId)).thenReturn(false);
		long productId = 1L;
		underTest.receiveDeleteOnProduct(googleId, productId);
		verify(productRepo, never()).deleteById(productId);
	}

	@Test
	public void shouldUpdateCategoryNameToFoo() {
		long categoryId = 1L;
		when(categoryRepo.findById(categoryId)).thenReturn(Optional.of(category));
		String name = "Foo";
		underTest.receivePutOnCategory(googleId, categoryId, name, false);
		verify(category).updateName(name);
	}

	@Test
	public void shouldNotUpdateCategoryNameIfNotAdmin() {
		when(loginService.isAdmin(googleId)).thenReturn(false);
		long categoryId = 1L;
		when(categoryRepo.findById(categoryId)).thenReturn(Optional.of(category));
		String name = "Foo";
		underTest.receivePutOnCategory(googleId, categoryId, name, false);
		verify(category, never()).updateName(name);
	}

	@Test
	public void shouldSaveTheCategoryAfterUpdating() {
		long categoryId = 1L;
		when(categoryRepo.findById(categoryId)).thenReturn(Optional.of(category));
		String name = "Bar";
		underTest.receivePutOnCategory(googleId, categoryId, name, false);
		InOrder inOrder = inOrder(categoryRepo, category);
		inOrder.verify(category).updateName(name);
		inOrder.verify(categoryRepo).save(category);
	}

	@Test
	public void shouldUpdateCategorySchoolAgeChildrenRequiredRuleAsTrue() {
		boolean schoolAgeChildrenRequired = true;
		long categoryId = 1L;
		String name = "";
		when(categoryRepo.findById(categoryId)).thenReturn(Optional.of(category));
		underTest.receivePutOnCategory(googleId, categoryId, name, schoolAgeChildrenRequired);
		verify(category).updateSchoolAgeChildrenRequired(schoolAgeChildrenRequired);
	}

}
