package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;

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
	private OAuth2User googleId;

	@Mock
	private Map<String, Object> attributes;

	private String googleName;

	@Mock
	private UserRepository userRepo;

	@Mock
	private PantryUser user;
	
	@Mock
	private EntityManager entitymanager;

	private String firstName;

	private String lastName;

	private String address;

	private int familySize;

	private String birthdate;

	private int schoolAgeChildren;

	private String zipCode;

	private String referral;

	private boolean hasInfants;

	private String primaryPhoneNumber;

	private String secondaryPhoneNumber;

	private String primaryEmail;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		when(token.getPrincipal()).thenReturn(googleId);
		when(googleId.getAttributes()).thenReturn(attributes);
		googleName = "12345";
		when(googleId.getName()).thenReturn(googleName);
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
		primaryPhoneNumber = "1234567890";
	}

	private String testReceiveRequestOnUser() {
		return underTest.receiveRequestOnUser(googleId, firstName, lastName, address, familySize, birthdate, schoolAgeChildren,
				zipCode, referral, hasInfants, primaryPhoneNumber, secondaryPhoneNumber, primaryEmail);
	}

	@Test
	public void shouldUpdateFirstNameWhenReceivingPostOnUser() {
		testReceiveRequestOnUser();
		verify(user).updateFirstName(firstName);
	}

	@Test
	public void shouldUpdateLastNameWhenReceivingPostOnUser() {
		testReceiveRequestOnUser();
		verify(user).updateLastName(lastName);
	}

	@Test
	public void shouldUpdateAddressWhenReceivingPostOnUser() {
		testReceiveRequestOnUser();
		verify(user).updateAddress(address);
	}

	@Test
	public void shouldUpdateFamilySizeWhenReceivingPostOnUser() {
		testReceiveRequestOnUser();
		verify(user).updateFamilySize(familySize);
	}

	@Test
	public void shouldUpdateBirthDateWhenReceivingPostOnUser() {
		testReceiveRequestOnUser();
		verify(user).updateBirthdate(birthdate);
	}

	@Test
	public void shouldRedirectBackToFormWhenReceivingPostOnUser() {
		String actual = testReceiveRequestOnUser();
		assertThat(actual, is("redirect:/settings"));
	}

	@Test
	public void shouldUpateSchoolAgeChildrenWhenReceivingRequestOnUser() {
		testReceiveRequestOnUser();
		verify(user).updateSchoolAgeChildren(schoolAgeChildren);
	}

	@Test
	public void shouldSaveUserAfterUpdatingWhenReceivingRequestOnUser() {
		testReceiveRequestOnUser();
		verify(userRepo).save(user);
	}

	@Test
	public void shouldUpdateZipCodeWhenReceivingRequestOnUser() {
		testReceiveRequestOnUser();
		verify(user).updateZipCode(zipCode);
	}

	@Test
	public void shouldUpdateReferralWhenReceivingRequestOnUser() {
		testReceiveRequestOnUser();
		verify(user).updateReferral(referral);
	}

	@Test
	public void shouldUpdateHasInfantsWhenReceivingRequestOnUser() {
		testReceiveRequestOnUser();
		verify(user).updateHasInfants(hasInfants);
	}

	@Test
	public void shouldNotUpdateSchoolAgeChildrenIfThereAreMoreThanTotalFamilySize() {
		familySize = 4;
		schoolAgeChildren = 5;
		testReceiveRequestOnUser();
		verify(user, never()).updateSchoolAgeChildren(schoolAgeChildren);
	}

	@Test
	public void shouldUpdatePrimaryPhoneNumberWhenReceivingRequestOnUser() {
		testReceiveRequestOnUser();
		verify(user).updatePrimaryPhoneNumber(primaryPhoneNumber);
	}

	@Test
	public void shouldUpdateSecondaryPhoneNumberWhenReceivingRequestOnUser() {
		testReceiveRequestOnUser();
		verify(user).updateSecondaryPhoneNumber(secondaryPhoneNumber);
	}

	@Test
	public void shouldUpdatePrimaryEmailWhenReceivingRequestOnUser() {
		testReceiveRequestOnUser();
		verify(user).updatePrimaryEmail(primaryEmail);
	}

}
