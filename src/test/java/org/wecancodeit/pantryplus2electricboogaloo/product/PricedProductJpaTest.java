package org.wecancodeit.pantryplus2electricboogaloo.product;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import javax.annotation.Resource;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.wecancodeit.pantryplus2electricboogaloo.currency.Currency;
import org.wecancodeit.pantryplus2electricboogaloo.currency.CurrencyRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PricedProductJpaTest {

	@Resource
	private ProductRepository productRepo;

	@Resource
	private CurrencyRepository currencyRepo;

	@Resource
	private TestEntityManager entityManager;

	@Test
	public void shouldSaveCurrencyToPricedProduct() {
		Currency currency = new Currency("Currency");
		currency = currencyRepo.save(currency);
		PricedProduct underTest = new PricedProduct("Product", null, "", false, 5, currency, 1);
		underTest = productRepo.save(underTest);
		long id = underTest.getId();
		entityManager.flush();
		entityManager.clear();
		underTest = (PricedProduct) productRepo.findById(id).get();
		Currency actual = underTest.getCurrency();
		assertThat(actual, is(currency));
	}

	@Test
	public void shouldSavePricedProductsToCurrency() {
		Currency underTest = new Currency("Currency");
		underTest = currencyRepo.save(underTest);
		long id = underTest.getId();
		PricedProduct product = new PricedProduct("First product", null, "", false, 1, underTest, 1);
		product = productRepo.save(product);
		PricedProduct anotherProduct = new PricedProduct("Second product", null, "", false, 5, underTest, 1);
		anotherProduct = productRepo.save(anotherProduct);
		entityManager.flush();
		entityManager.clear();
		underTest = currencyRepo.findById(id).get();
		Collection<PricedProduct> products = underTest.getPricedProducts();
		assertThat(products, Matchers.containsInAnyOrder(product, anotherProduct));
	}

}
