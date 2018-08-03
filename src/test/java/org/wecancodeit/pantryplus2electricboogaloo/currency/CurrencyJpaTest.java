package org.wecancodeit.pantryplus2electricboogaloo.currency;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.context.junit4.SpringRunner;
import org.wecancodeit.pantryplus2electricboogaloo.cart.CartRepository;
import org.wecancodeit.pantryplus2electricboogaloo.lineitem.LineItemRepository;
import org.wecancodeit.pantryplus2electricboogaloo.product.PricedProduct;
import org.wecancodeit.pantryplus2electricboogaloo.product.Product;
import org.wecancodeit.pantryplus2electricboogaloo.product.ProductRepository;
import org.wecancodeit.pantryplus2electricboogaloo.user.PantryUser;
import org.wecancodeit.pantryplus2electricboogaloo.user.UserRepository;

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

	@Resource
	private UserRepository userRepo;

	@Mock
	private OAuth2User googleId;

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

	@Test
	public void shouldHaveAllowanceBeOne() {
		PantryUser user = new PantryUser(googleId);
		Integer familySize = 2;
		user.updateFamilySize(familySize);
		user = userRepo.save(user);
		long userId = user.getId();
		Integer amountOfCurrency = 1;
		String computedMap = "{" + familySize + "=" + amountOfCurrency + "}";
		Currency underTest = new Currency("Currency", computedMap, "Units");
		underTest = currencyRepo.save(underTest);
		long underTestId = underTest.getId();
		entityManager.flush();
		entityManager.clear();
		user = userRepo.findById(userId).get();
		underTest = currencyRepo.findById(underTestId).get();
		int actual = underTest.allowanceOf(user);
		assertThat(actual, is(amountOfCurrency));
	}

	@Test
	public void shouldHaveAllowanceBeTwo() {
		PantryUser user = new PantryUser(googleId);
		Integer familySize = 3;
		user.updateFamilySize(familySize);
		user = userRepo.save(user);
		long userId = user.getId();
		Integer amountOfCurrency = 2;
		String allowanceMap = "{" + familySize + "=" + amountOfCurrency + "}";
		Currency underTest = new Currency("Currency", allowanceMap, "Units");
		underTest = currencyRepo.save(underTest);
		long underTestId = underTest.getId();
		entityManager.flush();
		entityManager.clear();
		user = userRepo.findById(userId).get();
		underTest = currencyRepo.findById(underTestId).get();
		int actual = underTest.allowanceOf(user);
		assertThat(actual, is(amountOfCurrency));
	}

	@Test
	public void shouldHaveAllowanceBeThree() {
		PantryUser user = new PantryUser(googleId);
		Integer familySize = 3;
		user.updateFamilySize(familySize);
		user = userRepo.save(user);
		long userId = user.getId();
		Integer amountOfCurrency = 3;
		String allowanceMap = "{" + familySize + "=" + amountOfCurrency + "}";
		Currency underTest = new Currency("Currency", allowanceMap, "Unites");
		underTest = currencyRepo.save(underTest);
		long underTestId = underTest.getId();
		entityManager.flush();
		entityManager.clear();
		user = userRepo.findById(userId).get();
		underTest = currencyRepo.findById(underTestId).get();
		int actual = underTest.allowanceOf(user);
		assertThat(actual, is(amountOfCurrency));
	}

	@Test
	public void shouldHaveAllowanceBeFive() {
		PantryUser user = new PantryUser(googleId);
		Integer familySize = 6;
		user.updateFamilySize(familySize);
		user = userRepo.save(user);
		long userId = user.getId();
		String allowanceMap = "{1=1, 2=2, 3=3, 4=4, 5=5}";
		Currency underTest = new Currency("Currency", allowanceMap, "Units");
		underTest = currencyRepo.save(underTest);
		long underTestId = underTest.getId();
		entityManager.flush();
		entityManager.clear();
		user = userRepo.findById(userId).get();
		underTest = currencyRepo.findById(underTestId).get();
		int actual = underTest.allowanceOf(user);
		assertThat(actual, is(5));
	}

	@Test
	public void shouldHaveAllowanceBeSix() {
		PantryUser user = new PantryUser(googleId);
		Integer familySize = 999;
		user.updateFamilySize(familySize);
		user = userRepo.save(user);
		long userId = user.getId();
		String allowanceMap = "{1=1, 2=2, 3=3, 4=4, 5=5, 6=6}";
		Currency underTest = new Currency("Currency", allowanceMap, "Units");
		underTest = currencyRepo.save(underTest);
		long underTestId = underTest.getId();
		entityManager.flush();
		entityManager.clear();
		user = userRepo.findById(userId).get();
		underTest = currencyRepo.findById(underTestId).get();
		int actual = underTest.allowanceOf(user);
		assertThat(actual, is(6));
	}

	@Test
	public void shouldHaveAllowanceBeSeven() {
		PantryUser user = new PantryUser(googleId);
		Integer familySize = 8;
		user.updateFamilySize(familySize);
		user = userRepo.save(user);
		long userId = user.getId();
		String allowanceMap = "{1=1, 3=3, 5=5, 7=7, 9=9}";
		Currency underTest = new Currency("Currency", allowanceMap, "Units");
		underTest = currencyRepo.save(underTest);
		long underTestId = underTest.getId();
		entityManager.flush();
		entityManager.clear();
		user = userRepo.findById(userId).get();
		underTest = currencyRepo.findById(underTestId).get();
		int actual = underTest.allowanceOf(user);
		assertThat(actual, is(7));
	}

	@Test
	public void shouldHaveLowerLimitOfZeroForGettingAllowance() {
		PantryUser user = new PantryUser(googleId);
		Integer familySize = 0;
		user.updateFamilySize(familySize);
		user = userRepo.save(user);
		long userId = user.getId();
		String allowanceMap = "{1=1, 3=3, 5=5, 7=7, 9=9}";
		Currency underTest = new Currency("Currency", allowanceMap, "Units");
		underTest = currencyRepo.save(underTest);
		long underTestId = underTest.getId();
		entityManager.flush();
		entityManager.clear();
		user = userRepo.findById(userId).get();
		underTest = currencyRepo.findById(underTestId).get();
		int actual = underTest.allowanceOf(user);
		assertThat(actual, is(1));
	}

	@Test
	public void shouldGetOddHashMap() {
		String check = "{1=1, 3=3, 5=5, 7=7}";
		Currency underTest = new Currency("Currency", check, "Units");
		String actual = underTest.getAllowanceMap();
		assertThat(actual, is(check));
	}

	@Test
	public void shouldGetEvenHashMap() {
		String check = "{2=2, 4=4, 6=6, 8=8}";
		Currency underTest = new Currency("Currency", check, "Units");
		String actual = underTest.getAllowanceMap();
		assertThat(actual, is(check));
	}

	@Test
	public void shouldUpdateMap() {
		String check = "{1=2, 2=4, 3=6, 4=8, 5=10}";
		Currency underTest = new Currency("Currency", check, "Units");
		String actual = underTest.getAllowanceMap();
		assertThat(actual, is(check));
	}

	@Test
	public void shouldUpdateMapWithoutWhitespace() {
		String representationMapWithoutWhiteSpace = "{1=2,2=4,3=6,4=8,5=10}";
		Currency underTest = new Currency("Currency", representationMapWithoutWhiteSpace, "Units");
		String actual = underTest.getAllowanceMap();
		String check = "{1=2, 2=4, 3=6, 4=8, 5=10}";
		assertThat(actual, is(check));
	}

}
