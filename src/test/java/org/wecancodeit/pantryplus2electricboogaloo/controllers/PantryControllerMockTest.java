package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.wecancodeit.pantryplus2electricboogaloo.category.Category;
import org.wecancodeit.pantryplus2electricboogaloo.category.CategoryRepository;

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

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldHaveDisplayUserFormReturnUserForm() {
		String templateName = "user-form";
		String actual = underTest.displayUserForm();
		assertThat(actual, is(templateName));
	}

	@Test
	public void shouldHaveDisplayShoppingReturnShopping() {
		String templateName = "shopping";
		String actual = underTest.displayShopping(model);
		assertThat(actual, is(templateName));
	}

	@Test
	public void shouldHaveDisplayCartReturnCart() {
		String templateName = "cart";
		String actual = underTest.displayCart();
		assertThat(actual, is(templateName));
	}

	@Test
	public void shouldHaveDisplayShoppingAddCategoriesToModel() {
		Iterable<Category> categories = asList(category, anotherCategory);
		when(categoryRepo.findAll()).thenReturn(categories);
		underTest.displayShopping(model);
		verify(model).addAttribute("categories", categories);
	}
}
