package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
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

public class UserControllerMockTest {

	@InjectMocks
	private UserController underTest;

	@Mock
	private OAuth2AuthenticationToken token;

	@Mock
	private OAuth2User oAuth2User;

	@Mock
	private Map<String, Object> attributes;

	private String googleName;

	@Mock
	private UserRepository userRepo;

	@Mock
	private PantryUser user;

	private String firstName;

	private String lastName;

	private String address;

	private int familySize;

	private String birthdate;

	private int schoolAgeChildren;

	private String zipCode;

	private String referral;

	private boolean hasInfants;

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
		birthdate = "September 13, 1969";
		schoolAgeChildren = 0;
		zipCode = "54321";
		referral = "COMPASS";
		hasInfants = false;
	}

	@Test
	public void shouldUpdateFirstNameWhenReceivingPostOnUser() {
		underTest.receiveRequestOnUser(token, firstName, lastName, address, familySize, birthdate, schoolAgeChildren,
				zipCode, referral, hasInfants);
		verify(user).updateFirstName(firstName);
	}

	@Test
	public void shouldUpdateLastNameWhenReceivingPostOnUser() {
		underTest.receiveRequestOnUser(token, firstName, lastName, address, familySize, birthdate, schoolAgeChildren,
				zipCode, referral, hasInfants);
		verify(user).updateLastName(lastName);
	}

	@Test
	public void shouldUpdateAddressWhenReceivingPostOnUser() {
		underTest.receiveRequestOnUser(token, firstName, lastName, address, familySize, birthdate, schoolAgeChildren,
				zipCode, referral, hasInfants);
		verify(user).updateAddress(address);
	}

	@Test
	public void shouldUpdateFamilySizeWhenReceivingPostOnUser() {
		underTest.receiveRequestOnUser(token, firstName, lastName, address, familySize, birthdate, schoolAgeChildren,
				zipCode, referral, hasInfants);
		verify(user).updateFamilySize(familySize);
	}

	@Test
	public void shouldUpdateBirthDateWhenReceivingPostOnUser() {
		underTest.receiveRequestOnUser(token, firstName, lastName, address, familySize, birthdate, schoolAgeChildren,
				zipCode, referral, hasInfants);
		verify(user).updateBirthdate(birthdate);
	}

	@Test
	public void shouldRedirectBackToFormWhenReceivingPostOnUser() {
		String actual = underTest.receiveRequestOnUser(token, firstName, lastName, address, familySize, birthdate,
				schoolAgeChildren, zipCode, referral, hasInfants);
		assertThat(actual, is("redirect:/settings"));
	}

	@Test
	public void shouldUpateSchoolAgeChildrenWhenReceivingRequestOnUser() {
		underTest.receiveRequestOnUser(token, firstName, lastName, address, familySize, birthdate, schoolAgeChildren,
				zipCode, referral, hasInfants);
		verify(user).updateSchoolAgeChildren(schoolAgeChildren);
	}

	@Test
	public void shouldSaveUserAfterUpdatingWhenReceivingRequestOnUser() {
		underTest.receiveRequestOnUser(token, firstName, lastName, address, familySize, birthdate, schoolAgeChildren,
				zipCode, referral, hasInfants);
		verify(userRepo).save(user);
	}

	@Test
	public void shouldUpdateZipCodeWhenReceivingRequestOnUser() {
		underTest.receiveRequestOnUser(token, firstName, lastName, address, familySize, birthdate, schoolAgeChildren,
				zipCode, referral, hasInfants);
		verify(user).updateZipCode(zipCode);
	}

	@Test
	public void shouldUpdateReferralWhenReceivingRequestOnUser() {
		underTest.receiveRequestOnUser(token, firstName, lastName, address, familySize, birthdate, schoolAgeChildren,
				zipCode, referral, hasInfants);
		verify(user).updateReferral(referral);
	}

	@Test
	public void shouldUpdateHasInfantsWhenReceivingRequestOnUser() {
		underTest.receiveRequestOnUser(token, firstName, lastName, address, familySize, birthdate, schoolAgeChildren,
				zipCode, referral, hasInfants);
		verify(user).updateHasInfants(hasInfants);
	}

	@Test
	public void shouldNotUpdateSchoolAgeChildrenIfThereAreMoreThanTotalFamilySize() {
		familySize = 4;
		schoolAgeChildren = 5;
		underTest.receiveRequestOnUser(token, firstName, lastName, address, familySize, birthdate, schoolAgeChildren,
				zipCode, referral, hasInfants);
		verify(user, never()).updateSchoolAgeChildren(schoolAgeChildren);
	}
}
