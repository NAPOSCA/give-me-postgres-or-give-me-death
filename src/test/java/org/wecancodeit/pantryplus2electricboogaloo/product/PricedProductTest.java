package org.wecancodeit.pantryplus2electricboogaloo.product;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.wecancodeit.pantryplus2electricboogaloo.currency.Currency;

public class PricedProductTest {

	@Test
	public void shouldHaveCouponCostOne() {
		Currency coupons = new Currency("Coupons");
		int cost = 1;
		PricedProduct underTest = new PricedProduct("Product", null, 5, coupons, cost, "");
		int actual = underTest.getPrice();
		assertThat(actual, is(cost));
	}

	@Test
	public void shouldHaveCouponCostTwo() {
		Currency coupons = new Currency("Coupons");
		int cost = 2;
		PricedProduct underTest = new PricedProduct("Product", null, 5, coupons, cost, "");
		int actual = underTest.getPrice();
		assertThat(actual, is(cost));
	}
	
	@Test
	public void shouldHaveTypeReturnPricedProduct() {
		PricedProduct underTest = new PricedProduct("Product", null, 1, null, 1, "");
		String actual = underTest.getType();
		assertThat(actual, is("PricedProduct"));
	}
	
}
