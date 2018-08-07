package org.wecancodeit.pantryplus2electricboogaloo.lineitem;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.wecancodeit.pantryplus2electricboogaloo.product.Product;

public class LineItemTest {

	@Test
	public void shouldHaveProduct() {
		Product product = new Product("Product", null, "", false);
		LineItem underTest = new LineItem(null, product);
		Product actual = underTest.getProduct();
		assertThat(actual, is(product));
	}

}
