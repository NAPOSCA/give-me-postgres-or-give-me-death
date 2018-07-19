package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

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

public class AdminMockMvcTest {
	
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
		String actualName =  adminController.displayAdminCategoriesView(model);
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
	public void shouldDisplayAdminProductView() {
		when(categoryRepo.findById(1L)).thenReturn(Optional.of(category));
		when(productRepo.findById(1L)).thenReturn(Optional.of(product));
		String actualName = adminController.displayAdminProductView(model, 1L, 1L);
		assertEquals(actualName, "admin/product");
	}
	
	@Test
	public void shouldPostProductForACategoryView() {
		when(categoryRepo.findById(1L)).thenReturn(Optional.of(category));
		String actualName = adminController.receiveAPostRequestOnACategorysProducts(model, 1L, "ok", "ok1", 1, 1, currency);
		assertEquals("redirect:/admin/categories/1", actualName);
	}
}
