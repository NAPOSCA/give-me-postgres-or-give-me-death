package org.wecancodeit.pantryplus2electricboogaloo.product;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.wecancodeit.pantryplus2electricboogaloo.category.Category;
import org.wecancodeit.pantryplus2electricboogaloo.user.PantryUser;

public class ProductTest {

	@Mock
	private Category category;

	@Mock
	private PantryUser user;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldHaveNameFoo() {
		Category category = new Category("Category", false);
		String name = "foo";
		Product underTest = new Product(name, category, "", false);
		String actualName = underTest.getName();
		assertThat(actualName, is(name));
	}

	@Test
	public void shouldHaveNameBar() {
		Category category = new Category("Category", false);
		String name = "bar";
		Product underTest = new Product(name, category, "", false);
		String actualName = underTest.getName();
		assertThat(actualName, is(name));
	}

	@Test
	public void shouldSaveCategoryCans() {
		Category category = new Category("Cans", false);
		Product underTest = new Product("Product", category, "", false);
		Category actual = underTest.getCategory();
		assertThat(actual, is(category));
	}

	@Test
	public void shouldHaveImageBeEmptyStringIfNoUrlSupplied() {
		Category category = new Category("Product", false);
		Product underTest = new Product("Product", category, "", false);
		String actual = underTest.getImage();
		assertThat(actual, is(""));
	}

	@Test
	public void shouldHaveImageBeFruit() {
		String imagePath = "http://www.images.com/fruit.png";
		Category category = new Category("Product", false);
		Product underTest = new Product("Product", category, imagePath, false);
		String actual = underTest.getImage();
		assertThat(actual, is(imagePath));
	}

	@Test
	public void shouldHaveImageBeVegtable() {
		String imagePath = "http://www.images.com/vetable.png";
		Category category = new Category("Product", false);
		Product underTest = new Product("Product", category, imagePath, false);
		String actual = underTest.getImage();
		assertThat(actual, is(imagePath));
	}

	@Test
	public void shouldHaveTypeReturnProduct() {
		Category category = new Category("Category", false);
		Product underTest = new Product("Product", category, "", false);
		String actual = underTest.getType();
		assertThat(actual, is("Product"));
	}

	@Test
	public void shouldBeVisibleIfUserHasInfantsAndProductHasRule() {
		boolean infantsRequired = true;
		Product underTest = new Product("Product", category, "", infantsRequired);
		Mockito.when(user.getHasInfants()).thenReturn(true);
		boolean actual = underTest.isVisibleTo(user);
		assertThat(actual, is(true));
	}

	@Test
	public void shouldNotBeVisibleIfUserHasNoInfantsAndProductHasRule() {
		boolean infantsRequired = true;
		Product underTest = new Product("Product", category, "", infantsRequired);
		Mockito.when(user.getHasInfants()).thenReturn(false);
		boolean actual = underTest.isVisibleTo(user);
		assertThat(actual, is(false));
	}

	@Test
	public void shouldBeVisibleIfUserHasNoInfantsAndProductDoesNotHaveRule() {
		boolean infantsRequired = false;
		Product underTest = new Product("Product", category, "", infantsRequired);
		Mockito.when(user.getHasInfants()).thenReturn(false);
		boolean actual = underTest.isVisibleTo(user);
		assertThat(actual, is(true));
	}

}
