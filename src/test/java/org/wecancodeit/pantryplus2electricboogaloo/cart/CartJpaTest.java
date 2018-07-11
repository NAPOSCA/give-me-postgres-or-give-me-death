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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.wecancodeit.pantryplus2electricboogaloo.lineitem.CountedLineItem;
import org.wecancodeit.pantryplus2electricboogaloo.lineitem.LineItem;
import org.wecancodeit.pantryplus2electricboogaloo.lineitem.LineItemRepository;
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

	PantryUser user;
	Cart cart;
	long userId;
	long cartId;
	String googleName = "12345";

	@Test
	public void shouldSaveAndLoadCart() {
		user = new PantryUser(googleName);
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
		user = new PantryUser(googleName);
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
		PantryUser user = userRepo.save(new PantryUser("12345"));
		Cart cart = cartRepo.save(new Cart(user));
		long cartId = cart.getId();
		PantryUser anotherUser = userRepo.save(new PantryUser("54321"));
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
		PantryUser user = userRepo.save(new PantryUser("12345"));
		Cart cart = cartRepo.save(new Cart(user));
		long cartId = cart.getId();
		LineItem lineItem = lineItemRepo.save(new LineItem(cart));
		entityManager.flush();
		entityManager.clear();
		cart = cartRepo.findById(cartId).get();
		Set<LineItem> actual = cart.getLineItems();
		assertThat(actual, contains(lineItem));
	}
	
	@Test
	public void shouldSaveCountedLineItemToCart() {
		PantryUser user = userRepo.save(new PantryUser("12345"));
		Cart cart = cartRepo.save(new Cart(user));
		long cartId = cart.getId();
		CountedLineItem countedLineItem = lineItemRepo.save(new CountedLineItem(cart));
		entityManager.flush();
		entityManager.clear();
		cart = cartRepo.findById(cartId).get();
		Set<CountedLineItem> actual = cart.getCountedLineItems();
		assertThat(actual, hasItem(countedLineItem));
	}
	
	@Test
	public void shouldSaveLineItemAndCountedLineItemToCartButOnlyReturnLineItem() {
		PantryUser user = userRepo.save(new PantryUser("12345"));
		Cart cart = cartRepo.save(new Cart(user));
		long cartId = cart.getId();
		LineItem lineItem = lineItemRepo.save(new LineItem(cart));
		lineItemRepo.save(new CountedLineItem(cart));
		entityManager.flush();
		entityManager.clear();
		cart = cartRepo.findById(cartId).get();
		Set<LineItem> actual = cart.getLineItems();
		assertThat(actual, contains(lineItem));
	}
}
