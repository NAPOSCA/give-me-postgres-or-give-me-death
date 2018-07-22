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
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.wecancodeit.pantryplus2electricboogaloo.user.PantryUser;
import org.wecancodeit.pantryplus2electricboogaloo.user.UserRepository;

public class UserRestControllerMockTest {

	@InjectMocks
	private UserRestController underTest;

	private String firstName;

	@Mock
	private PantryUser user;

	@Mock
	private OAuth2User googleId;

	@Mock
	private Map<String, Object> attributes;

	private String googleName;

	@Mock
	private UserRepository userRepo;

	private String lastName;

	private String address;

	private int familySize;

	private String birthdate;

	private int schoolAgeChildren;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		when(googleId.getAttributes()).thenReturn(attributes);
		googleName = "12345";
		when(attributes.get("sub")).thenReturn(googleName);
		when(userRepo.findByGoogleName(googleName)).thenReturn(Optional.of(user));
		firstName = "Scooby";
		lastName = "Doo";
		address = "Mystery Van";
		familySize = 5;
		birthdate = "September 13, 1969";
		schoolAgeChildren = 0;
	}

	@Test
	public void shouldUpdateFirstNameWhenReceivingPatchOnFirstName() {
		underTest.receivePatchForFirstName(googleId, firstName);
		verify(user).updateFirstName(firstName);
	}

	@Test
	public void shouldReturnFirstNameWhenReceivingPatchOnFirstName() {
		String actual = underTest.receivePatchForFirstName(googleId, firstName);
		assertThat(actual, is(firstName));
	}

	@Test
	public void shouldUpdateLastNameWhenReceivingPatchOnLastName() {
		underTest.receivePatchForLastName(googleId, lastName);
		verify(user).updateLastName(lastName);
	}

	@Test
	public void shouldReturnLastNameWhenReceivingPatchOnLastName() {
		String actual = underTest.receivePatchForLastName(googleId, lastName);
		assertThat(actual, is(lastName));
	}

	@Test
	public void shouldUpdateAddressWhenReceivingPatchOnAddress() {
		underTest.receivePatchForAddress(googleId, address);
		verify(user).updateAddress(address);
	}

	@Test
	public void shouldReturnAddressWhenReceivingPatchOnAddress() {
		String actual = underTest.receivePatchForAddress(googleId, address);
		assertThat(actual, is(address));
	}

	@Test
	public void shouldUpdateFamilySizeWhenReceivingPatchOnFamilySize() {
		underTest.receivePatchForFamilySize(googleId, familySize);
		verify(user).updateFamilySize(familySize);
	}

	@Test
	public void shouldReturnFamilySizeWhenReceivingPatchOnFamilySize() {
		int actual = underTest.receivePatchForFamilySize(googleId, familySize);
		assertThat(actual, is(familySize));
	}

	@Test
	public void shouldUpdateBirthDateWhenReceivingPatchOnBirthDate() {
		underTest.receivePatchForBirthDate(googleId, birthdate);
		verify(user).updateBirthdate(birthdate);
	}

	@Test
	public void shouldReturnBirthDateWhenReceivingPatchOnBirthDate() {
		String actual = underTest.receivePatchForBirthDate(googleId, birthdate);
		assertThat(actual, is(birthdate));
	}

	@Test
	public void shouldUpdateSchoolAgeChildrenWhenReceivingPatchOnSchoolAgeChildren() {
		underTest.receivePatchForSchoolAgeChildren(googleId, schoolAgeChildren);
		verify(user).updateSchoolAgeChildren(schoolAgeChildren);
	}

	@Test
	public void shouldReturnSchoolAgeChildrenWhenReceivingPatchOnSchoolAgeChildren() {
		int actual = underTest.receivePatchForSchoolAgeChildren(googleId, schoolAgeChildren);
		assertThat(actual, is(schoolAgeChildren));
	}

}
