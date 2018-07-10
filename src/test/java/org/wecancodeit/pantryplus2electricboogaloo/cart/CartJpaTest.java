package org.wecancodeit.pantryplus2electricboogaloo.cart;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Optional;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.wecancodeit.pantryplus2electricboogaloo.user.User;
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

	User user;
	Cart cart;
	long userId;
	long cartId;
	String googleName = "12345";

	@Test
	public void shouldSaveAndLoadCart() {
		user = new User(googleName);
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
		user = new User(googleName);
		user = userRepo.save(user);
		userId = user.getId();
		cart = cartRepo.save(new Cart(user));
		cartId = cart.getId();
		entityManager.flush();
		entityManager.clear();
		Optional<User> potentialUser = userRepo.findById(userId);
		if (potentialUser.isPresent()) {
			user = potentialUser.get();
			assertThat(user.getCart(), is(cart));
		} else {
			fail("user not found");
		}
	}

	@Test
	public void shouldSaveTwoCarts() {
		User user = userRepo.save(new User("12345"));
		Cart cart = cartRepo.save(new Cart(user));
		long cartId = cart.getId();
		User anotherUser = userRepo.save(new User("54321"));
		Cart anotherCart = cartRepo.save(new Cart(anotherUser));
		long anotherCartId = anotherCart.getId();
		entityManager.flush();
		entityManager.clear();
		Optional<Cart> potentialCart = cartRepo.findById(cartId);
		Optional<Cart> potentialAnotherCart = cartRepo.findById(anotherCartId);
		assertThat(potentialCart.isPresent(), is(true));
		assertThat(potentialAnotherCart.isPresent(), is(true));
	}
}
