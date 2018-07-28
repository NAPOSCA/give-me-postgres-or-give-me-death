package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.wecancodeit.pantryplus2electricboogaloo.LoginService;
import org.wecancodeit.pantryplus2electricboogaloo.category.Category;
import org.wecancodeit.pantryplus2electricboogaloo.category.CategoryRepository;
import org.wecancodeit.pantryplus2electricboogaloo.currency.Currency;
import org.wecancodeit.pantryplus2electricboogaloo.currency.CurrencyRepository;
import org.wecancodeit.pantryplus2electricboogaloo.product.Product;
import org.wecancodeit.pantryplus2electricboogaloo.product.ProductRepository;

public class AdminControllerMockTest {

	@InjectMocks
	private AdministrationController underTest;

	@Mock
	private Model model;

	@Mock
	private CategoryRepository categoryRepo;

	@Mock
	private Category category;

	@Mock
	private Category category2;

	@Mock
	private ProductRepository productRepo;

	@Mock
	private Product product;

	@Mock
	private OAuth2User googleId;

	@Mock
	private LoginService loginService;

	@Mock
	private Currency currency;

	@Mock
	private Currency anotherCurrency;

	@Mock
	private CurrencyRepository currencyRepo;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		when(loginService.isAdmin(googleId)).thenReturn(true);
	}

	@Test
	public void shouldReturnAdminCategoriesView() {
		String actualName = underTest.displayAdminCategoriesView(googleId, model);
		assertEquals(actualName, "admin/categories");
	}

	@Test
	public void shouldAttachCategoriesToModel() {
		Iterable<Category> categories = asList(category, category2);
		when(categoryRepo.findAll()).thenReturn(categories);
		underTest.displayAdminCategoriesView(googleId, model);
		verify(model).addAttribute("categories", categories);
	}

	@Test
	public void shouldReturnAdminCategoryView() {
		when(categoryRepo.findById(1L)).thenReturn(Optional.of(category));
		String actualName = underTest.displayAdminCategoryView(googleId, model, 1L);
		assertEquals(actualName, "admin/category");
	}

	@Test
	public void shouldAttachOneCategoryToModel() {
		when(categoryRepo.findById(1L)).thenReturn(Optional.of(category));
		underTest.displayAdminCategoryView(googleId, model, 1L);
		verify(model).addAttribute("category", category);
	}

	@Test
	public void shouldRedirectToCategoriesViewIfCategoryIsNotPresent() {
		String templateName = underTest.displayAdminCategoryView(googleId, model, 1L);
		assertThat(templateName, is("redirect:/admin/categories"));
	}

	@Test
	public void shouldDisplayAdminProductView() {
		when(categoryRepo.findById(1L)).thenReturn(Optional.of(category));
		when(productRepo.findById(1L)).thenReturn(Optional.of(product));
		String actualName = underTest.displayAdminProductView(googleId, model, 1L, 1L);
		assertEquals(actualName, "admin/product");
	}

	@Test
	public void shouldRedirectToCategoryViewOneIfProductIsNotPresent() {
		long categoryId = 1L;
		when(categoryRepo.findById(categoryId)).thenReturn(Optional.of(category));
		String templateName = underTest.displayAdminProductView(googleId, model, categoryId, 1L);
		assertThat(templateName, is("redirect:/admin/categories/" + categoryId));
	}

	@Test
	public void shouldRedirectToCategoryViewTwoIfProductIsNotPresent() {
		long categoryId = 2L;
		when(categoryRepo.findById(categoryId)).thenReturn(Optional.of(category));
		String templateName = underTest.displayAdminProductView(googleId, model, categoryId, 1L);
		assertThat(templateName, is("redirect:/admin/categories/" + categoryId));
	}

	@Test
	public void shouldRedirectToFirstCategoryViewWhenReceivingPostRequestOnProductsOfACategory() {
		long categoryId = 1L;
		String productName = "product";
		String type = "Product";
		int maximumQuantity = 5;
		int valueInCurrency = 1;
		when(categoryRepo.findById(categoryId)).thenReturn(Optional.of(category));
		long currencyId = 1L;
		String templateName = underTest.receivePostRequestOnProductsOfCategory(googleId, categoryId, productName, type,
				maximumQuantity, valueInCurrency, currencyId);
		assertThat(templateName, is("redirect:/admin/categories/" + categoryId));
	}

	@Test
	public void shouldRedirectToSecondCategoryViewWhenReceivingPostRequestOnProductsOfACategory() {
		long categoryId = 2L;
		String productName = "product";
		String type = "Product";
		int maximumQuantity = 5;
		int valueInCurrency = 1;
		when(categoryRepo.findById(categoryId)).thenReturn(Optional.of(category));
		long currencyId = 1L;
		String templateName = underTest.receivePostRequestOnProductsOfCategory(googleId, categoryId, productName, type,
				maximumQuantity, valueInCurrency, currencyId);
		assertThat(templateName, is("redirect:/admin/categories/" + categoryId));
	}

	@Test
	public void shouldDisplayMainAdminView() {
		String templateName = underTest.displayAdminView(googleId);
		assertThat(templateName, is("admin/index"));
	}

	@Test(expected = AccessDeniedException.class)
	public void shouldDenyAccessIfUserIsNotAdminBeforeDisplayingTheAdminView() {
		when(loginService.isAdmin(googleId)).thenReturn(false);
		underTest.displayAdminView(googleId);
	}

	@Test(expected = AccessDeniedException.class)
	public void shouldDenyAccessIfUserIsNotAdminBeforeDisplayingTheAdminCategoriesView() {
		when(loginService.isAdmin(googleId)).thenReturn(false);
		underTest.displayAdminCategoriesView(googleId, model);
	}

	@Test(expected = AccessDeniedException.class)
	public void shouldDenyAccessIfUserIsNotAdminBeforeDisplayingTheProductView() {
		when(loginService.isAdmin(googleId)).thenReturn(false);
		underTest.displayAdminProductView(googleId, model, 1L, 1L);
	}

	@Test(expected = AccessDeniedException.class)
	public void shouldDenyAccessIfUserIsNotAdminBeforeDisplayingTheAdminCategoryView() {
		when(loginService.isAdmin(googleId)).thenReturn(false);
		underTest.displayAdminCategoryView(googleId, model, 1L);
	}

	@Test(expected = AccessDeniedException.class)
	public void shouldDenyAccessIfUserIsNotAdminBeforeSavingACategory() {
		when(loginService.isAdmin(googleId)).thenReturn(false);
		underTest.receivePostRequestOnCategories(googleId, "");
	}

	@Test(expected = AccessDeniedException.class)
	public void shouldDenyAccessIfUserIsNotAdminBeforeSavingProduct() {
		when(loginService.isAdmin(googleId)).thenReturn(false);
		long categoryId = 3L;
		String productName = "Coupons";
		String type = "Product";
		int maximumQuantity = 5;
		int valueInCurrency = 1;
		long currencyId = 1L;
		underTest.receivePostRequestOnProductsOfCategory(googleId, categoryId, productName, type, maximumQuantity,
				valueInCurrency, currencyId);
	}

	@Test(expected = AccessDeniedException.class)
	public void shouldHaveDisplayCurrenciesViewDenyIfUserIsNotAdmin() {
		when(loginService.isAdmin(googleId)).thenReturn(false);
		underTest.displayCurrenciesView(googleId, model);
	}

	@Test(expected = AccessDeniedException.class)
	public void shouldReceivePostRequestOnCurrenciesAndDenyIfUserIsNotAdmin() {
		when(loginService.isAdmin(googleId)).thenReturn(false);
		underTest.receivePostRequestOnCurrencies(googleId, "Coupons");
	}

	@Test(expected = AccessDeniedException.class)
	public void shouldHaveDisplayCurrencyViewDenyIfUserIsNotAdmin() {
		when(loginService.isAdmin(googleId)).thenReturn(false);
		underTest.displayCurrencyView(googleId, model, 1L);
	}

	@Test
	public void shouldDisplayCurrenciesView() {
		String templateName = underTest.displayCurrenciesView(googleId, model);
		assertThat(templateName, is("admin/currencies"));
	}

	@Test
	public void shouldAttachAllCurrenciesToModelWhenDisplayingCurrenciesView() {
		Iterable<Currency> currencies = asList(currency, anotherCurrency);
		when(currencyRepo.findAll()).thenReturn(currencies);
		underTest.displayCurrenciesView(googleId, model);
		verify(model).addAttribute("currencies", currencies);
	}

	@Test
	public void shouldDisplayCurrencyView() {
		long currencyId = 1L;
		when(currencyRepo.findById(currencyId)).thenReturn(Optional.of(currency));
		String templateName = underTest.displayCurrencyView(googleId, model, currencyId);
		assertThat(templateName, is("admin/currency"));
	}

	@Test
	public void shouldAttachOneCurrencyToModelWhenDisplayingCurrencyView() {
		long currencyId = 1L;
		when(currencyRepo.findById(currencyId)).thenReturn(Optional.of(currency));
		underTest.displayCurrencyView(googleId, model, currencyId);
		verify(model).addAttribute("currency", currency);
	}

	@Test
	public void shouldRedirectToCurrenciesViewIfCurrencyDoesNotExist() {
		String templateName = underTest.displayCurrencyView(googleId, model, 1L);
		assertThat(templateName, is("redirect:/admin/currencies"));
	}

}
