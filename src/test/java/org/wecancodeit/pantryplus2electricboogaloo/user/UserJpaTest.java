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
		String googleName = "12345";
		PantryUser user = userRepo.save(new PantryUser(googleName));
		long id = user.getId();
		entityManager.flush();
		entityManager.clear();
		boolean isPresent = userRepo.findById(id).isPresent();
		assertThat(isPresent, is(true));
	}
	
	@Test
	public void shouldSaveTwoUsers() {
		userRepo.save(new PantryUser("12345"));
		userRepo.save(new PantryUser("54321"));
	}
}
