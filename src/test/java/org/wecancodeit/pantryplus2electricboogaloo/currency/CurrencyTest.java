package org.wecancodeit.pantryplus2electricboogaloo.currency;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CurrencyTest {

	@Test
	public void shouldHaveNameFoo() {
		String name = "Foo";
		Currency underTest = new Currency(name);
		String actual = underTest.getName();
		assertThat(actual, is(name));
	}

	@Test
	public void shouldHaveNameBar() {
		String name = "Bar";
		Currency underTest = new Currency(name);
		String actual = underTest.getName();
		assertThat(actual, is(name));
	}

	@Test
	public void shouldHaveUnitLbs() {
		String unit = "Lbs";
		Currency underTest = new Currency("", "", unit);
		String actual = underTest.getUnit();
		assertThat(actual, is(unit));
	}

	@Test
	public void shouldHaveUnitCoupons() {
		String unit = "Coupons";
		Currency underTest = new Currency("", "", unit);
		String actual = underTest.getUnit();
		assertThat(actual, is(unit));
	}

}
