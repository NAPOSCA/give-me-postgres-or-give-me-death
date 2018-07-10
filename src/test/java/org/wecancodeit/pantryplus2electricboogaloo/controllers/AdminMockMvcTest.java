package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.wecancodeit.pantryplus2electricboogaloo.category.CategoryRepository;

public class AdminMockMvcTest {
	
	@InjectMocks
	private AdministrationController adminController;
	
	@Mock
	private Model model;
	
	@Mock
	private CategoryRepository categoryRepo;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}	
	
	@Test
	public void shouldReturnAdminCategoryView() {
		String templateName = "admin/categories";
		String actualName =  adminController.displayAdminCategoriesView(model);
		assertEquals(actualName, templateName);
	}
}
