package org.wecancodeit.pantryplus2electricboogaloo.cart;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Optional;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.context.junit4.SpringRunner;
import org.wecancodeit.pantryplus2electricboogaloo.currency.Currency;
import org.wecancodeit.pantryplus2electricboogaloo.currency.CurrencyRepository;
import org.wecancodeit.pantryplus2electricboogaloo.lineitem.CountedLineItem;
import org.wecancodeit.pantryplus2electricboogaloo.lineitem.LineItem;
import org.wecancodeit.pantryplus2electricboogaloo.lineitem.LineItemRepository;
import org.wecancodeit.pantryplus2electricboogaloo.product.LimitedProduct;
import org.wecancodeit.pantryplus2electricboogaloo.product.PricedProduct;
import org.wecancodeit.pantryplus2electricboogaloo.product.ProductRepository;
import org.wecancodeit.pantryplus2electricboogaloo.user.PantryUser;
import org.wecancodeit.pantryplus2electricboogaloo.user.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CartJpaTest {

	@Resource
	private TestEntityManager entityManager;

	@Resource
	private CartRepository cartRepo;

	@Resource
	private UserRepository userRepo;

	@Resource
	private LineItemRepository lineItemRepo;

	@Resource
	private CurrencyRepository currencyRepo;

	@Resource
	private ProductRepository productRepo;

	PantryUser user;
	Cart cart;
	long userId;
	long cartId;
	String googleName = "12345";

	@Mock
	private OAuth2User googleId;

	@Test
	public void shouldSaveAndLoadCart() {
		user = new PantryUser(googleId);
		cart = new Cart(user);
		user = userRepo.save(user);
		userId = user.getId();
		cart = cartRepo.save(cart);
		cartId = cart.getId();
		entityManager.flush();
		entityManager.clear();
		Optional<Cart> potentialCart = cartRepo.findById(cartId);
		boolean isPresent = potentialCart.isPresent();
		assertThat(isPresent, is(true));
	}

	@Test
	public void shouldSaveAndLoadCartByUser() {
		user = new PantryUser(googleId);
		user = userRepo.save(user);
		userId = user.getId();
		cart = cartRepo.save(new Cart(user));
		cartId = cart.getId();
		entityManager.flush();
		entityManager.clear();
		Optional<PantryUser> potentialUser = userRepo.findById(userId);
		if (potentialUser.isPresent()) {
			user = potentialUser.get();
			assertThat(user.getCart(), is(cart));
		} else {
			fail("user not found");
		}
	}

	@Test
	public void shouldSaveTwoCarts() {
		PantryUser user = userRepo.save(new PantryUser(googleId));
		Cart cart = cartRepo.save(new Cart(user));
		long cartId = cart.getId();
		PantryUser anotherUser = userRepo.save(new PantryUser(googleId));
		Cart anotherCart = cartRepo.save(new Cart(anotherUser));
		long anotherCartId = anotherCart.getId();
		entityManager.flush();
		entityManager.clear();
		Optional<Cart> potentialCart = cartRepo.findById(cartId);
		Optional<Cart> potentialAnotherCart = cartRepo.findById(anotherCartId);
		assertThat(potentialCart.isPresent(), is(true));
		assertThat(potentialAnotherCart.isPresent(), is(true));
	}

	@Test
	public void shouldSaveLineItemToCart() {
		PantryUser user = userRepo.save(new PantryUser(googleId));
		Cart cart = cartRepo.save(new Cart(user));
		long cartId = cart.getId();
		LineItem lineItem = lineItemRepo.save(new LineItem(cart, null));
		entityManager.flush();
		entityManager.clear();
		cart = cartRepo.findById(cartId).get();
		Set<LineItem> actual = cart.getLineItems();
		assertThat(actual, contains(lineItem));
	}

	@Test
	public void shouldSaveCountedLineItemToCart() {
		PantryUser user = userRepo.save(new PantryUser(googleId));
		Cart cart = cartRepo.save(new Cart(user));
		long cartId = cart.getId();
		CountedLineItem countedLineItem = lineItemRepo.save(new CountedLineItem(cart, null));
		entityManager.flush();
		entityManager.clear();
		cart = cartRepo.findById(cartId).get();
		Set<CountedLineItem> actual = cart.getCountedLineItems();
		assertThat(actual, hasItem(countedLineItem));
	}

	@Test
	public void shouldSaveLineItemAndCountedLineItemToCartButOnlyReturnLineItem() {
		PantryUser user = userRepo.save(new PantryUser(googleId));
		Cart cart = cartRepo.save(new Cart(user));
		long cartId = cart.getId();
		LineItem lineItem = lineItemRepo.save(new LineItem(cart, null));
		lineItemRepo.save(new CountedLineItem(cart, null));
		entityManager.flush();
		entityManager.clear();
		cart = cartRepo.findById(cartId).get();
		Set<LineItem> actual = cart.getLineItems();
		assertThat(actual, contains(lineItem));
	}

	@Test
	public void shouldHaveAmountUsedBeOne() {
		Cart underTest = cartRepo.save(new Cart(null));
		long underTestId = underTest.getId();
		Currency currency = currencyRepo.save(new Currency("Coupons"));
		long currencyId = currency.getId();
		int cost = 1;
		PricedProduct product = productRepo.save(new PricedProduct("Apple", null, 5, currency, cost, ""));
		lineItemRepo.save(new CountedLineItem(underTest, product));
		entityManager.flush();
		entityManager.clear();
		currency = currencyRepo.findById(currencyId).get();
		underTest = cartRepo.findById(underTestId).get();
		int actual = underTest.amountUsed(currency);
		assertThat(actual, is(cost));
	}

	@Test
	public void shouldHaveAmountUsedBeTwo() {
		Cart underTest = cartRepo.save(new Cart(null));
		long underTestId = underTest.getId();
		Currency currency = currencyRepo.save(new Currency("Coupons"));
		long currencyId = currency.getId();
		int cost = 2;
		PricedProduct product = productRepo.save(new PricedProduct("Apple", null, 5, currency, cost, ""));
		lineItemRepo.save(new CountedLineItem(underTest, product));
		entityManager.flush();
		entityManager.clear();
		currency = currencyRepo.findById(currencyId).get();
		underTest = cartRepo.findById(underTestId).get();
		int actual = underTest.amountUsed(currency);
		assertThat(actual, is(cost));
	}

	@Test
	public void shouldHaveAmountUsedBeFour() {
		Cart underTest = cartRepo.save(new Cart(null));
		long underTestId = underTest.getId();
		Currency currency = currencyRepo.save(new Currency("Coupons"));
		long currencyId = currency.getId();
		PricedProduct product = productRepo.save(new PricedProduct("Apple", null, 5, currency, 1, ""));
		CountedLineItem lineItem = new CountedLineItem(underTest, product);
		lineItem.increase();
		lineItemRepo.save(lineItem);
		PricedProduct anotherProduct = productRepo.save(new PricedProduct("Banana", null, 5, currency, 2, ""));
		lineItemRepo.save(new CountedLineItem(underTest, anotherProduct));
		LimitedProduct limitedProduct = new LimitedProduct("Kiwi", null, 5, "");
		productRepo.save(limitedProduct);
		lineItemRepo.save(new CountedLineItem(underTest, limitedProduct));
		entityManager.flush();
		entityManager.clear();
		currency = currencyRepo.findById(currencyId).get();
		underTest = cartRepo.findById(underTestId).get();
		int actual = underTest.amountUsed(currency);
		assertThat(actual, is(4));
	}

}
