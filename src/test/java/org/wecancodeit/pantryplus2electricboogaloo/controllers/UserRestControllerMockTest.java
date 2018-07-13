package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.wecancodeit.pantryplus2electricboogaloo.user.PantryUser;
import org.wecancodeit.pantryplus2electricboogaloo.user.UserRepository;

public class UserRestControllerMockTest {

	@InjectMocks
	private UserRestController underTest;

	private String firstName;

	@Mock
	private OAuth2AuthenticationToken token;

	@Mock
	private PantryUser user;

	@Mock
	private OAuth2User oAuth2User;

	@Mock
	private Map<String, Object> attributes;

	private String googleName;

	@Mock
	private UserRepository userRepo;

	private String lastName;

	private String address;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		when(token.getPrincipal()).thenReturn(oAuth2User);
		when(oAuth2User.getAttributes()).thenReturn(attributes);
		googleName = "12345";
		when(attributes.get("sub")).thenReturn(googleName);
		when(userRepo.findByGoogleName(googleName)).thenReturn(Optional.of(user));
		firstName = "Scooby";
		lastName = "Doo";
		address = "Mystery Van";
	}

	@Test
	public void shouldHaveUpdateUsersFirstNameUpdateFirstName() {
		underTest.updateUsersFirstName(token, firstName);
		verify(user).updateFirstName(firstName);
	}

	@Test
	public void shouldHaveUpdateUsersLastNameUpdateLastName() {
		underTest.updateUsersLastName(token, lastName);
		verify(user).updateLastName(lastName);
	}

	@Test
	public void shouldHaveUpdateUsersAddressUpdateAddress() {
		underTest.updateUsersAddress(token, address);
		verify(user).updateAddress(address);
	}
}
