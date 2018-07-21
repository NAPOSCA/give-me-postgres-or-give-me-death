package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.wecancodeit.pantryplus2electricboogaloo.category.Category;
import org.wecancodeit.pantryplus2electricboogaloo.category.CategoryRepository;
import org.wecancodeit.pantryplus2electricboogaloo.currency.Currency;
import org.wecancodeit.pantryplus2electricboogaloo.product.LimitedProduct;
import org.wecancodeit.pantryplus2electricboogaloo.product.PricedProduct;
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
	private LimitedProduct limitedProduct;

	@Mock
	private PricedProduct pricedProduct;

	@Mock
	private Currency currency;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldReturnAdminCategoriesView() {
		String actualName = adminController.displayAdminCategoriesView(model);
		assertEquals(actualName, "admin/categories");
	}

	@Test
	public void shouldAttachCategoryToModel() {
		Iterable<Category> categories = asList(category, category2);
		when(categoryRepo.findAll()).thenReturn(categories);
		adminController.displayAdminCategoriesView(model);
		verify(model).addAttribute("categories", categories);
	}

	@Test
	public void shouldReturnAdminCategoryView() {
		when(categoryRepo.findById(1L)).thenReturn(Optional.of(category));
		String actualName = adminController.displayAdminCategoryView(model, 1L);
		assertEquals(actualName, "admin/category");
	}

	@Test
	public void shouldAttachOneCategoryToModel() {
		when(categoryRepo.findById(1L)).thenReturn(Optional.of(category));
		adminController.displayAdminCategoryView(model, 1L);
		verify(model).addAttribute("category", category);
	}

	@Test
	public void shouldRedirectToCategoriesViewIfCategoryIsNotPresent() {
		String templateName = adminController.displayAdminCategoryView(model, 1L);
		assertThat(templateName, is("redirect:/admin/categories"));
	}

	@Test
	public void shouldDisplayAdminProductView() {
		when(categoryRepo.findById(1L)).thenReturn(Optional.of(category));
		when(productRepo.findById(1L)).thenReturn(Optional.of(product));
		String actualName = adminController.displayAdminProductView(model, 1L, 1L);
		assertEquals(actualName, "admin/product");
	}

	@Test
	public void shouldRedirectToCategoryViewOneIfProductIsNotPresent() {
		long categoryId = 1L;
		when(categoryRepo.findById(categoryId)).thenReturn(Optional.of(category));
		String templateName = adminController.displayAdminProductView(model, categoryId, 1L);
		assertThat(templateName, is("redirect:/admin/categories/" + categoryId));
	}

	@Test
	public void shouldRedirectToCategoryViewTwoIfProductIsNotPresent() {
		long categoryId = 2L;
		when(categoryRepo.findById(categoryId)).thenReturn(Optional.of(category));
		String templateName = adminController.displayAdminProductView(model, categoryId, 1L);
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
		String templateName = adminController.receiveAPostRequestOnACategorysProducts(model, categoryId, productName,
				type, maximumQuantity, valueInCurrency, currencyName);
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
		String templateName = adminController.receiveAPostRequestOnACategorysProducts(model, categoryId, productName,
				type, maximumQuantity, valueInCurrency, currencyName);
		assertThat(templateName, is("redirect:/admin/categories/" + categoryId));
	}

}
