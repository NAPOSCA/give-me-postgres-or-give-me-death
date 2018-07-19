package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
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

	private int familySize;

	private String birthDate;

	private int schoolAgeChildren;

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
		familySize = 5;
		birthDate = "September 13, 1969";
		schoolAgeChildren = 0;
	}

	@Test
	public void shouldUpdateFirstNameWhenReceivingPatchOnFirstName() {
		underTest.receivePatchForFirstName(token, firstName);
		verify(user).updateFirstName(firstName);
	}

	@Test
	public void shouldReturnFirstNameWhenReceivingPatchOnFirstName() {
		String actual = underTest.receivePatchForFirstName(token, firstName);
		assertThat(actual, is(firstName));
	}

	@Test
	public void shouldUpdateLastNameWhenReceivingPatchOnLastName() {
		underTest.receivePatchForLastName(token, lastName);
		verify(user).updateLastName(lastName);
	}

	@Test
	public void shouldReturnLastNameWhenReceivingPatchOnLastName() {
		String actual = underTest.receivePatchForLastName(token, lastName);
		assertThat(actual, is(lastName));
	}

	@Test
	public void shouldUpdateAddressWhenReceivingPatchOnAddress() {
		underTest.receivePatchForAddress(token, address);
		verify(user).updateAddress(address);
	}

	@Test
	public void shouldReturnAddressWhenReceivingPatchOnAddress() {
		String actual = underTest.receivePatchForAddress(token, address);
		assertThat(actual, is(address));
	}

	@Test
	public void shouldUpdateFamilySizeWhenReceivingPatchOnFamilySize() {
		underTest.receivePatchForFamilySize(token, familySize);
		verify(user).updateFamilySize(familySize);
	}

	@Test
	public void shouldReturnFamilySizeWhenReceivingPatchOnFamilySize() {
		int actual = underTest.receivePatchForFamilySize(token, familySize);
		assertThat(actual, is(familySize));
	}

	@Test
	public void shouldUpdateBirthDateWhenReceivingPatchOnBirthDate() {
		underTest.receivePatchForBirthDate(token, birthDate);
		verify(user).updateBirthDate(birthDate);
	}

	@Test
	public void shouldReturnBirthDateWhenReceivingPatchOnBirthDate() {
		String actual = underTest.receivePatchForBirthDate(token, birthDate);
		assertThat(actual, is(birthDate));
	}

	@Test
	public void shouldUpdateSchoolAgeChildrenWhenReceivingPatchOnSchoolAgeChildren() {
		underTest.receivePatchForSchoolAgeChildren(token, schoolAgeChildren);
		verify(user).updateSchoolAgeChildren(schoolAgeChildren);
	}

	@Test
	public void shouldReturnSchoolAgeChildrenWhenReceivingPatchOnSchoolAgeChildren() {
		int actual = underTest.receivePatchForSchoolAgeChildren(token, schoolAgeChildren);
		assertThat(actual, is(schoolAgeChildren));
	}

}
