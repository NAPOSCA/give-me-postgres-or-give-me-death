package org.wecancodeit.pantryplus2electricboogaloo.user;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserJpaTest {
	
	@Resource
	private UserRepository userRepo;
	
	@Resource
	private TestEntityManager entityManager;

	@Test
	public void shouldSaveAndLoadUser() {
		String firstName = "Alex";
		String lastName = "Malcolm";
		int familySize = 1;
		int schoolAgeChildren = 0;
		boolean hasInfants = false;
		String pickupDate = "2018-04-04";
		String zipCode = "00000";
		String address = "1234 Main St";
		String birthdate = "January 1st, 1969";
		User user = new User(firstName, lastName, familySize, schoolAgeChildren, hasInfants, pickupDate, zipCode, address, birthdate);
		user = userRepo.save(user);
		long id = user.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		boolean isPresent = userRepo.findById(id).isPresent();
		assertThat(isPresent, is(true));
	}
	
	@Test
	public void shouldSaveTwoUsers() {
		userRepo.save(new User("", "", 1, 0, false, "2018-04-04", "", "", ""));
		userRepo.save(new User("", "", 1, 0, false, "2018-04-04", "", "", ""));
	}
}
