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
		Product underTest = new Product(name, category);
		String actualName = underTest.getName();
		assertThat(actualName, is(name));
	}
	
	@Test
	public void shouldHaveNameBar() {
		Category category = new Category("Category");
		String name = "bar";
		Product underTest = new Product(name, category);
		String actualName = underTest.getName();
		assertThat(actualName, is(name));
	}
	
	@Test
	public void shouldSaveCategoryCans() {
		Category category = new Category("Cans");
		Product underTest = new Product("Product", category);
		Category actual = underTest.getCategory();
		assertThat(actual, is(category));
	}
}
