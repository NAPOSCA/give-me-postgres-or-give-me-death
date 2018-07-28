package org.wecancodeit.pantryplus2electricboogaloo;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.wecancodeit.pantryplus2electricboogaloo.user.PantryUser;
import org.wecancodeit.pantryplus2electricboogaloo.user.UserRepository;

public class LoginServiceTest {

	@InjectMocks
	private LoginService loginService;

	@Mock
	private OAuth2User googleId;

	private String googleName;

	@Mock
	private UserRepository userRepo;

	@Mock
	private PantryUser user;

	@Mock
	private EntityManager entityManager;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		googleName = "12345";
		when(googleId.getName()).thenReturn(googleName);
		when(userRepo.findByGoogleName(googleName)).thenReturn(Optional.of(user));
	}

	@Test
	public void shouldRetrieveUser() {
		PantryUser actual = loginService.resolveUser(googleId);
		assertThat(actual, is(user));
	}

	@Test
	public void shouldHaveAlexMalcolmBeAdmin() {
		when(googleId.getName()).thenReturn("115969733168111031226");
		boolean actual = loginService.isAdmin(googleId);
		assertTrue(actual);
	}

	@Test
	public void shouldHaveSomeoneElseNotBeAdmin() {
		when(googleId.getName()).thenReturn("000000000000000000000");
		boolean actual = loginService.isAdmin(googleId);
		assertThat(actual, is(false));
	}

}
