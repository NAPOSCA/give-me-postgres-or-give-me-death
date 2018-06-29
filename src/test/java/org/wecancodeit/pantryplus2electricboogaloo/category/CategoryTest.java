package org.wecancodeit.pantryplus2electricboogaloo.category;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CategoryTest {
	
	@Test
	public void shouldGetNameFoo() {
		String name = "foo";
		Category underTest = new Category(name);
		String actualName = underTest.getName();
		assertThat(actualName, is(name));
	}
	
	@Test
	public void shouldGetNameBar() {
		String name = "bar";
		Category underTest = new Category(name);
		String actualName = underTest.getName();
		assertThat(actualName, is(name));
	}
}
