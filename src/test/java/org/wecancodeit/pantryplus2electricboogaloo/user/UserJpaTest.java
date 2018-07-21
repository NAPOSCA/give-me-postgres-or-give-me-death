package org.wecancodeit.pantryplus2electricboogaloo.user;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserJpaTest {

	@Resource
	private UserRepository userRepo;

	@Resource
	private TestEntityManager entityManager;

	@Mock
	private OAuth2User googleId;

	@Mock
	private Map<String, Object> googleAttributes;

	@Test
	public void shouldSaveAndLoadUser() {
		PantryUser user = userRepo.save(new PantryUser(googleId));
		long id = user.getId();
		entityManager.flush();
		entityManager.clear();
		boolean isPresent = userRepo.findById(id).isPresent();
		assertThat(isPresent, is(true));
	}

	@Test
	public void shouldSaveTwoUsers() {
		userRepo.save(new PantryUser(googleId));
		userRepo.save(new PantryUser(googleId));
	}

	@Test
	public void shouldSaveAndLoadUserByGoogleName12345() {
		String googleName = "12345";
		when(googleId.getAttributes()).thenReturn(googleAttributes);
		when(googleAttributes.get("sub")).thenReturn(googleName);
		userRepo.save(new PantryUser(googleId));
		entityManager.flush();
		entityManager.clear();
		boolean isPresent = userRepo.findByGoogleName(googleName).isPresent();
		assertThat(isPresent, is(true));
	}
}
