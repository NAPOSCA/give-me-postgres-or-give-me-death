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
import org.wecancodeit.pantryplus2electricboogaloo.product.Product;
import org.wecancodeit.pantryplus2electricboogaloo.product.ProductRepository;

public class AdminMockTest {

	@InjectMocks
	private AdministrationController adminController;

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

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		when(loginService.isAdmin(googleId)).thenReturn(true);
	}

	@Test
	public void shouldReturnAdminCategoriesView() {
		String actualName = adminController.displayAdminCategoriesView(googleId, model);
		assertEquals(actualName, "admin/categories");
	}

	@Test
	public void shouldAttachCategoryToModel() {
		Iterable<Category> categories = asList(category, category2);
		when(categoryRepo.findAll()).thenReturn(categories);
		adminController.displayAdminCategoriesView(googleId, model);
		verify(model).addAttribute("categories", categories);
	}

	@Test
	public void shouldReturnAdminCategoryView() {
		when(categoryRepo.findById(1L)).thenReturn(Optional.of(category));
		String actualName = adminController.displayAdminCategoryView(googleId, model, 1L);
		assertEquals(actualName, "admin/category");
	}

	@Test
	public void shouldAttachOneCategoryToModel() {
		when(categoryRepo.findById(1L)).thenReturn(Optional.of(category));
		adminController.displayAdminCategoryView(googleId, model, 1L);
		verify(model).addAttribute("category", category);
	}

	@Test
	public void shouldRedirectToCategoriesViewIfCategoryIsNotPresent() {
		String templateName = adminController.displayAdminCategoryView(googleId, model, 1L);
		assertThat(templateName, is("redirect:/admin/categories"));
	}

	@Test
	public void shouldDisplayAdminProductView() {
		when(categoryRepo.findById(1L)).thenReturn(Optional.of(category));
		when(productRepo.findById(1L)).thenReturn(Optional.of(product));
		String actualName = adminController.displayAdminProductView(googleId, model, 1L, 1L);
		assertEquals(actualName, "admin/product");
	}

	@Test
	public void shouldRedirectToCategoryViewOneIfProductIsNotPresent() {
		long categoryId = 1L;
		when(categoryRepo.findById(categoryId)).thenReturn(Optional.of(category));
		String templateName = adminController.displayAdminProductView(googleId, model, categoryId, 1L);
		assertThat(templateName, is("redirect:/admin/categories/" + categoryId));
	}

	@Test
	public void shouldRedirectToCategoryViewTwoIfProductIsNotPresent() {
		long categoryId = 2L;
		when(categoryRepo.findById(categoryId)).thenReturn(Optional.of(category));
		String templateName = adminController.displayAdminProductView(googleId, model, categoryId, 1L);
		assertThat(templateName, is("redirect:/admin/categories/" + categoryId));
	}

	@Test
	public void shouldRedirectToFirstCategoryViewWhenReceivingPostRequestOnProductsOfACategory() {
		long categoryId = 1L;
		String productName = "product";
		String type = "Product";
		int maximumQuantity = 5;
		int valueInCurrency = 1;
		String currencyName = "coupons";
		when(categoryRepo.findById(categoryId)).thenReturn(Optional.of(category));
		String templateName = adminController.receiveAPostRequestOnACategorysProducts(googleId, model, categoryId,
				productName, type, maximumQuantity, valueInCurrency, currencyName);
		assertThat(templateName, is("redirect:/admin/categories/" + categoryId));
	}

	@Test
	public void shouldRedirectToSecondCategoryViewWhenReceivingPostRequestOnProductsOfACategory() {
		long categoryId = 2L;
		String productName = "product";
		String type = "Product";
		int maximumQuantity = 5;
		int valueInCurrency = 1;
		String currencyName = "coupons";
		when(categoryRepo.findById(categoryId)).thenReturn(Optional.of(category));
		String templateName = adminController.receiveAPostRequestOnACategorysProducts(googleId, model, categoryId,
				productName, type, maximumQuantity, valueInCurrency, currencyName);
		assertThat(templateName, is("redirect:/admin/categories/" + categoryId));
	}
	
	@Test
	public void shouldDisplayMainAdminView() {
		String templateName = adminController.displayAdminView(googleId);
		assertThat(templateName, is("admin/index"));
	}
	
	@Test(expected = AccessDeniedException.class)
	public void shouldDenyAccessIfUserIsNotAdminBeforeDisplayingTheAdminView() {
		when(loginService.isAdmin(googleId)).thenReturn(false);
		adminController.displayAdminView(googleId);
	}
	
	@Test(expected = AccessDeniedException.class)
	public void shouldDenyAccessIfUserIsNotAdminBeforeDisplayingTheAdminCategoriesView() {
		when(loginService.isAdmin(googleId)).thenReturn(false);
		adminController.displayAdminCategoriesView(googleId, model);
	}
	
	@Test(expected = AccessDeniedException.class)
	public void shouldDenyAccessIfUserIsNotAdminBeforeDisplayingTheProductView() {
		when(loginService.isAdmin(googleId)).thenReturn(false);
		adminController.displayAdminProductView(googleId, model, 1L, 1L);
	}
	
	@Test(expected = AccessDeniedException.class)
	public void shouldDenyAccessIfUserIsNotAdminBeforeDisplayingTheAdminCategoryView() {
		when(loginService.isAdmin(googleId)).thenReturn(false);
		adminController.displayAdminCategoryView(googleId, model, 1L);
	}
	
	@Test(expected = AccessDeniedException.class)
	public void shouldDenyAccessIfUserIsNotAdminBeforeSavingACategory() {
		when(loginService.isAdmin(googleId)).thenReturn(false);
		adminController.receiveAPostRequestOnCategories(googleId, "");
	}
	
	@Test(expected = AccessDeniedException.class)
	public void shouldDenyAccessIfUserIsNotAdminBeforeSavingProduct() {
		when(loginService.isAdmin(googleId)).thenReturn(false);
		adminController.receiveAPostRequestOnACategorysProducts(googleId, model, 1L, "", "", 1, 1, "");
	}

}
