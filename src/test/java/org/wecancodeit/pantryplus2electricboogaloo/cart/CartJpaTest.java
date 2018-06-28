package org.wecancodeit.pantryplus2electricboogaloo.cart;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import javax.annotation.Resource;

import org.junit.Before;
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
	
	String birthdate;
	String firstName;
	String lastName;
	int familySize;
	String address;
	User user;
	Cart cart;
	int schoolAgeChildren;
	boolean hasInfants;
	String pickupDateString;
	String zipCode;
	long userId;
	long cartId;
	
	@Before
	public void setup() {
		birthdate = "January 1st 1969";
		firstName = "Foobeedoobeedo";
		lastName = "lasty namey";
		familySize = 4;
		address = "1234 Main St";
		schoolAgeChildren = 1;
		hasInfants = false;
		pickupDateString = "2018-04-09";
		zipCode = "43201";
		user = new User(firstName, lastName, familySize, schoolAgeChildren, hasInfants, pickupDateString, zipCode, address, birthdate);
		cart = new Cart(user);
		
		user = userRepo.save(user);
		userId = user.getId();
		cart = cartRepo.save(cart);
		cartId = cart.getId();
	}

	@Test
	public void shouldSaveAndLoadCart() {
		entityManager.flush();
		entityManager.clear();
		Optional<Cart> potentialCart = cartRepo.findById(cartId);
		boolean isPresent = potentialCart.isPresent();
		assertThat(isPresent, is(true));
	}
}
