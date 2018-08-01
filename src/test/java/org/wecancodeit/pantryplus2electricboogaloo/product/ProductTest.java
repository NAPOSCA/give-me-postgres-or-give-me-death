package org.wecancodeit.pantryplus2electricboogaloo.product;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.wecancodeit.pantryplus2electricboogaloo.category.Category;

public class ProductTest {

	@Test
	public void shouldHaveNameFoo() {
		Category category = new Category("Category");
		String name = "foo";
		Product underTest = new Product(name, category, "");
		String actualName = underTest.getName();
		assertThat(actualName, is(name));
	}

	@Test
	public void shouldHaveNameBar() {
		Category category = new Category("Category");
		String name = "bar";
		Product underTest = new Product(name, category, "");
		String actualName = underTest.getName();
		assertThat(actualName, is(name));
	}

	@Test
	public void shouldSaveCategoryCans() {
		Category category = new Category("Cans");
		Product underTest = new Product("Product", category, "");
		Category actual = underTest.getCategory();
		assertThat(actual, is(category));
	}

	@Test
	public void shouldHaveImageBeEmptyStringIfNoUrlSupplied() {
		Category category = new Category("Product");
		Product underTest = new Product("Product", category, "");
		String actual = underTest.getImage();
		assertThat(actual, is(""));
	}

	@Test
	public void shouldHaveImageBeFruit() {
		String imagePath = "http://www.images.com/fruit.png";
		Category category = new Category("Product");
		Product underTest = new Product("Product", category, imagePath);
		String actual = underTest.getImage();
		assertThat(actual, is(imagePath));
	}

	@Test
	public void shouldHaveImageBeVegtable() {
		String imagePath = "http://www.images.com/vetable.png";
		Category category = new Category("Product");
		Product underTest = new Product("Product", category, imagePath);
		String actual = underTest.getImage();
		assertThat(actual, is(imagePath));
	}

	@Test
	public void shouldHaveTypeReturnProduct() {
		Category category = new Category("Category");
		Product underTest = new Product("Product", category, "");
		String actual = underTest.getType();
		assertThat(actual, is("Product"));
	}

}
