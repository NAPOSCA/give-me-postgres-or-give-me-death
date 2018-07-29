package org.wecancodeit.pantryplus2electricboogaloo.product;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class LimitedProductTest {

	@Test
	public void shouldHaveMaximumQuantityOne() {
		int maximumQuantity = 1;
		LimitedProduct underTest = new LimitedProduct("Product", null, maximumQuantity);
		int actual = underTest.getMaximumQuantity();
		assertThat(actual, is(maximumQuantity));
	}

	@Test
	public void shouldHaveMaximumQuantityTwo() {
		int maximumQuantity = 2;
		LimitedProduct underTest = new LimitedProduct("Product", null, maximumQuantity);
		int actual = underTest.getMaximumQuantity();
		assertThat(actual, is(maximumQuantity));
	}
}
