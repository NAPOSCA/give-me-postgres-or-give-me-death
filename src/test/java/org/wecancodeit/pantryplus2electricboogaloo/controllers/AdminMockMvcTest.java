package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.wecancodeit.pantryplus2electricboogaloo.category.Category;
import org.wecancodeit.pantryplus2electricboogaloo.category.CategoryRepository;

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
}
