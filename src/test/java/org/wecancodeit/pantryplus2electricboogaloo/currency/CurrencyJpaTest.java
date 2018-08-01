package org.wecancodeit.pantryplus2electricboogaloo.currency;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.wecancodeit.pantryplus2electricboogaloo.cart.CartRepository;
import org.wecancodeit.pantryplus2electricboogaloo.lineitem.LineItemRepository;
import org.wecancodeit.pantryplus2electricboogaloo.product.PricedProduct;
import org.wecancodeit.pantryplus2electricboogaloo.product.Product;
import org.wecancodeit.pantryplus2electricboogaloo.product.ProductRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CurrencyJpaTest {

	@Resource
	private CurrencyRepository currencyRepo;

	@Resource
	private TestEntityManager entityManager;

	@Resource
	private ProductRepository productRepo;

	@Resource
	private CartRepository cartRepo;

	@Resource
	private LineItemRepository lineItemRepo;

	@Test
	public void shouldSaveAndLoad() {
		Currency currency = new Currency("Coupons");
		currency = currencyRepo.save(currency);
		long id = currency.getId();

		entityManager.flush();
		entityManager.clear();

		boolean isPresent = currencyRepo.findById(id).isPresent();
		assertThat(isPresent, is(true));
	}

	@Test
	public void shouldSaveAndLoadTwoObjects() {
		currencyRepo.save(new Currency("First Currency"));
		currencyRepo.save(new Currency("Second Currency"));
	}
	
	@Test
	public void shouldDeletePricedProductWhenCurrencyIsDeleted() {
		Currency underTest = new Currency("Coupons");
		underTest = currencyRepo.save(underTest);
		long underTestId = underTest.getId();
		PricedProduct product = new PricedProduct("Product", null, 1, underTest, 1, "");
		product = productRepo.save(product);
		long productId = product.getId();
		entityManager.flush();
		entityManager.clear();
		currencyRepo.deleteById(underTestId);
		Optional<Product> potentialProduct = productRepo.findById(productId);
		boolean isPresent = potentialProduct.isPresent();
		assertThat(isPresent, is(false));
	}

}
