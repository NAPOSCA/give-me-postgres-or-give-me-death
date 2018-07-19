package org.wecancodeit.pantryplus2electricboogaloo.user;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class UserTest {

	int familySize;
	String googleName = "12345";

	@Mock
	private OAuth2User googleId;

	@Mock
	private Map<String, Object> googleAttributes;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		when(googleId.getAttributes()).thenReturn(googleAttributes);
	}

	@Test
	public void shouldCalculateCouponTotalForOnePerson() {
		familySize = 1;
		PantryUser user = new PantryUser(googleId);
		user.updateFamilySize(familySize);
		int couponLimit = user.calculateCouponLimit();
		assertThat(couponLimit, is(10));
	}

	@Test
	public void shouldCalculateCouponTotalForTwoPersonFamily() {
		familySize = 2;
		PantryUser user = new PantryUser(googleId);
		user.updateFamilySize(familySize);
		int couponLimit = user.calculateCouponLimit();
		assertThat(couponLimit, is(10));
	}

	@Test
	public void shouldCalculateCouponTotalForThreePersonFamily() {
		familySize = 3;
		PantryUser user = new PantryUser(googleId);
		user.updateFamilySize(familySize);
		int couponLimit = user.calculateCouponLimit();
		assertThat(couponLimit, is(20));
	}

	@Test
	public void shouldCalculateCouponTotalForFourPersonFamily() {
		familySize = 4;
		PantryUser user = new PantryUser(googleId);
		user.updateFamilySize(familySize);
		int couponLimit = user.calculateCouponLimit();
		assertThat(couponLimit, is(20));
	}

	@Test
	public void shouldCalculateCouponTotalForFivePersonFamily() {
		familySize = 5;
		PantryUser user = new PantryUser(googleId);
		user.updateFamilySize(familySize);
		int couponLimit = user.calculateCouponLimit();
		assertThat(couponLimit, is(25));
	}

	@Test
	public void shouldCalculateCouponTotalForSixPersonFamily() {
		familySize = 6;
		PantryUser user = new PantryUser(googleId);
		user.updateFamilySize(familySize);
		int couponLimit = user.calculateCouponLimit();
		assertThat(couponLimit, is(25));
	}

	@Test
	public void shouldCalculateCouponTotalForSevenPersonFamily() {
		familySize = 7;
		PantryUser user = new PantryUser(googleId);
		user.updateFamilySize(familySize);
		int couponLimit = user.calculateCouponLimit();
		assertThat(couponLimit, is(30));
	}

	@Test
	public void shouldCalculateCouponTotalForEightPersonFamily() {
		familySize = 8;
		PantryUser user = new PantryUser(googleId);
		user.updateFamilySize(familySize);
		int couponLimit = user.calculateCouponLimit();
		assertThat(couponLimit, is(30));
	}

	@Test
	public void shouldCalculateCouponTotalForNinePersonFamily() {
		familySize = 9;
		PantryUser user = new PantryUser(googleId);
		user.updateFamilySize(familySize);
		int couponLimit = user.calculateCouponLimit();
		assertThat(couponLimit, is(35));
	}

	@Test
	public void shouldCalculateMeatLimitForOnePerson() {
		familySize = 1;
		PantryUser user = new PantryUser(googleId);
		user.updateFamilySize(familySize);
		int meatLimit = user.calculateMeatLimit();
		assertThat(meatLimit, is(4));
	}

	@Test
	public void shouldCalculateMeatLimitForTwoPersonFamily() {
		familySize = 2;
		PantryUser user = new PantryUser(googleId);
		user.updateFamilySize(familySize);
		int meatLimit = user.calculateMeatLimit();
		assertThat(meatLimit, is(4));
	}

	@Test
	public void shouldCalculateMeatLimitForThreePersonFamily() {
		familySize = 3;
		PantryUser user = new PantryUser(googleId);
		user.updateFamilySize(familySize);
		int meatLimit = user.calculateMeatLimit();
		assertThat(meatLimit, is(6));
	}

	@Test
	public void shouldCalculateMeatLimitForFourPersonFamily() {
		familySize = 4;
		PantryUser user = new PantryUser(googleId);
		user.updateFamilySize(familySize);
		int meatLimit = user.calculateMeatLimit();
		assertThat(meatLimit, is(6));
	}

	@Test
	public void shouldCalculateMeatLimitForFivePersonFamily() {
		familySize = 5;
		PantryUser user = new PantryUser(googleId);
		user.updateFamilySize(familySize);
		int meatLimit = user.calculateMeatLimit();
		assertThat(meatLimit, is(6));
	}

	@Test
	public void shouldCalculateMeatLimitForSixPersonFamily() {
		familySize = 6;
		PantryUser user = new PantryUser(googleId);
		user.updateFamilySize(familySize);
		int meatLimit = user.calculateMeatLimit();
		assertThat(meatLimit, is(8));
	}

	@Test
	public void shouldBeValidUserIfAllInformationIsPresent() {
		PantryUser user = new PantryUser(googleId);
		user.updateAddress("1234 Main St");
		user.updateBirthdate("January 30th 1902");
		user.updateFamilySize(2);
		user.updateFirstName("First");
		user.updateLastName("Last");
		user.updateSchoolAgeChildren(0);
		user.updateZipCode("12345");
		user.updateReferral("COMPASS");
		user.updatePrimaryPhoneNumber("1234567890");
		boolean isValid = user.isValid();
		assertThat(isValid, is(true));
	}

	@Test
	public void shouldNotBeValidUserIfAddressIsMissing() {
		PantryUser user = new PantryUser(googleId);
		user.updateBirthdate("January 30th 1902");
		user.updateFamilySize(2);
		user.updateFirstName("First");
		user.updateLastName("Last");
		user.updateSchoolAgeChildren(0);
		user.updateZipCode("12345");
		boolean isValid = user.isValid();
		assertThat(isValid, is(false));
	}

	@Test
	public void shouldNotBeValidUserIfBirthDateIsMissing() {
		PantryUser user = new PantryUser(googleId);
		user.updateAddress("1234 Main St");
		user.updateFamilySize(2);
		user.updateFirstName("First");
		user.updateLastName("Last");
		user.updateSchoolAgeChildren(0);
		user.updateZipCode("12345");
		boolean isValid = user.isValid();
		assertThat(isValid, is(false));
	}

	@Test
	public void shouldNotBeValidUserIfFamilySizeIsMissing() {
		PantryUser user = new PantryUser(googleId);
		user.updateAddress("1234 Main St");
		user.updateBirthdate("January 30th 1902");
		user.updateFirstName("First");
		user.updateLastName("Last");
		user.updateSchoolAgeChildren(0);
		user.updateZipCode("12345");
		boolean isValid = user.isValid();
		assertThat(isValid, is(false));
	}

	@Test
	public void shouldNotBeValidUserIfFirstNameIsMissing() {
		PantryUser user = new PantryUser(googleId);
		user.updateAddress("1234 Main St");
		user.updateBirthdate("January 30th 1902");
		user.updateFamilySize(2);
		user.updateLastName("Last");
		user.updateSchoolAgeChildren(0);
		user.updateZipCode("12345");
		boolean isValid = user.isValid();
		assertThat(isValid, is(false));
	}

	@Test
	public void shouldNotBeValidUserIfLastNameIsMissing() {
		PantryUser user = new PantryUser(googleId);
		user.updateAddress("1234 Main St");
		user.updateBirthdate("January 30th 1902");
		user.updateFamilySize(2);
		user.updateFirstName("First");
		user.updateSchoolAgeChildren(0);
		user.updateZipCode("12345");
		boolean isValid = user.isValid();
		assertThat(isValid, is(false));
	}

	@Test
	public void shouldNotBeValidUserIfSchoolAgeChildrenIsMissing() {
		PantryUser user = new PantryUser(googleId);
		user.updateAddress("1234 Main St");
		user.updateBirthdate("January 30th 1902");
		user.updateFamilySize(2);
		user.updateFirstName("First");
		user.updateLastName("Last");
		user.updateZipCode("12345");
		boolean isValid = user.isValid();
		assertThat(isValid, is(false));
	}

	@Test
	public void shouldNotBeValidIfAddressIsBlank() {
		PantryUser user = new PantryUser(googleId);
		user.updateAddress("");
		user.updateBirthdate("January 30th 1902");
		user.updateFamilySize(2);
		user.updateFirstName("First");
		user.updateLastName("Last");
		user.updateSchoolAgeChildren(0);
		user.updateZipCode("12345");
		boolean isValid = user.isValid();
		assertThat(isValid, is(false));
	}

	@Test
	public void shouldNotBeValidIfBirthDateIsBlank() {
		PantryUser user = new PantryUser(googleId);
		user.updateAddress("1234 Main St");
		user.updateBirthdate("");
		user.updateFamilySize(2);
		user.updateFirstName("First");
		user.updateLastName("Last");
		user.updateSchoolAgeChildren(0);
		user.updateZipCode("12345");
		boolean isValid = user.isValid();
		assertThat(isValid, is(false));
	}

	@Test
	public void shouldNotBeValidIfFamilySizeIsZero() {
		PantryUser user = new PantryUser(googleId);
		user.updateAddress("1234 Main St");
		user.updateBirthdate("January 30th 1902");
		user.updateFamilySize(0);
		user.updateFirstName("First");
		user.updateLastName("Last");
		user.updateSchoolAgeChildren(0);
		user.updateZipCode("12345");
		boolean isValid = user.isValid();
		assertThat(isValid, is(false));
	}

	@Test
	public void shouldNotBeValidIfFirstNameIsBlank() {
		PantryUser user = new PantryUser(googleId);
		user.updateAddress("1234 Main St");
		user.updateBirthdate("January 30th 1902");
		user.updateFamilySize(2);
		user.updateFirstName("");
		user.updateLastName("Last");
		user.updateSchoolAgeChildren(0);
		user.updateZipCode("12345");
		boolean isValid = user.isValid();
		assertThat(isValid, is(false));
	}

	@Test
	public void shouldNotBeValidIfLastNameIsBlank() {
		PantryUser user = new PantryUser(googleId);
		user.updateAddress("1234 Main St");
		user.updateBirthdate("January 30th 1902");
		user.updateFamilySize(2);
		user.updateFirstName("First");
		user.updateLastName("");
		user.updateSchoolAgeChildren(0);
		user.updateZipCode("12345");
		boolean isValid = user.isValid();
		assertThat(isValid, is(false));
	}

	@Test
	public void shouldNotBeValidIfZipCodeIsMissing() {
		PantryUser user = new PantryUser(googleId);
		user.updateAddress("1234 Main St");
		user.updateBirthdate("January 30th 1902");
		user.updateFamilySize(2);
		user.updateFirstName("First");
		user.updateLastName("Last");
		user.updateSchoolAgeChildren(0);
		boolean isValid = user.isValid();
		assertThat(isValid, is(false));
	}

	@Test
	public void shouldNotBeValidIfReferralIsMissing() {
		PantryUser user = new PantryUser(googleId);
		user.updateAddress("1234 Main St");
		user.updateBirthdate("January 30th 1902");
		user.updateFamilySize(2);
		user.updateFirstName("First");
		user.updateLastName("Last");
		user.updateSchoolAgeChildren(0);
		user.updateZipCode("12345");
		boolean isValid = user.isValid();
		assertThat(isValid, is(false));
	}

	@Test
	public void shouldHaveInfantsBeTrue() {
		boolean check = true;
		PantryUser user = new PantryUser(googleId);
		user.updateHasInfants(check);
		boolean actual = user.getHasInfants();
		assertThat(actual, is(check));
	}

	@Test
	public void shouldHaveInfantsBeFalse() {
		boolean check = false;
		PantryUser user = new PantryUser(googleId);
		user.updateHasInfants(check);
		boolean actual = user.getHasInfants();
		assertThat(actual, is(check));
	}

	@Test
	public void shouldNotBeValidIfPhoneNumberIsMissing() {
		PantryUser user = new PantryUser(googleId);
		user.updateAddress("1234 Main St");
		user.updateBirthdate("January 30th 1902");
		user.updateFamilySize(2);
		user.updateFirstName("First");
		user.updateLastName("Last");
		user.updateSchoolAgeChildren(0);
		user.updateZipCode("12345");
		user.updateReferral("COMPASS");
		boolean isValid = user.isValid();
		assertThat(isValid, is(false));
	}

	@Test
	public void shouldInitializeFirstNameFromGoogleIdAsPhil() {
		String name = "Phil";
		when(googleAttributes.get("given_name")).thenReturn(name);
		PantryUser user = new PantryUser(googleId);
		String actual = user.getFirstName();
		assertThat(actual, is(name));
	}

	@Test
	public void shouldInitializeLastNameFromGoogleIdAsCollins() {
		String name = "Collins";
		when(googleAttributes.get("family_name")).thenReturn(name);
		PantryUser user = new PantryUser(googleId);
		String actual = user.getLastName();
		assertThat(actual, is(name));
	}

	@Test
	public void shouldInitializeGoogleEmailFromGoogleId() {
		String email = "coolguy69@geemail.net";
		when(googleAttributes.get("email")).thenReturn(email);
		PantryUser user = new PantryUser(googleId);
		String actual = user.getGoogleEmail();
		assertThat(actual, is(email));
	}

	@Test
	public void shouldInitializePrimaryEmailFromGoogleId() {
		String email = "12345@6789.com";
		when(googleAttributes.get("email")).thenReturn(email);
		PantryUser user = new PantryUser(googleId);
		String actual = user.getPrimaryEmail();
		assertThat(actual, is(email));
	}

	@Test
	public void shouldHaveGoogleEmailAndPrimaryEmailBeTheSameInitially() {
		String email = "a@b.c";
		when(googleAttributes.get("email")).thenReturn(email);
		PantryUser user = new PantryUser(googleId);
		String primary = user.getPrimaryEmail();
		String google = user.getGoogleEmail();
		assertThat(primary, is(google));
	}

	@Test
	public void shouldSetOneAsSecondaryPhoneNumber() {
		String secondaryPhoneNumber = "1";
		PantryUser user = new PantryUser(googleId);
		user.updateSecondaryPhoneNumber(secondaryPhoneNumber);
		String actual = user.getSecondaryPhoneNumber();
		assertThat(actual, is(secondaryPhoneNumber));
	}

	@Test
	public void shouldSetTwoAsSecondaryPhoneNumber() {
		String secondaryPhoneNumber = "2";
		PantryUser user = new PantryUser(googleId);
		user.updateSecondaryPhoneNumber(secondaryPhoneNumber);
		String actual = user.getSecondaryPhoneNumber();
		assertThat(actual, is(secondaryPhoneNumber));
	}

	@Test
	public void shouldUpdatePrimaryEmailToAlpha() {
		String email = "Alpha";
		PantryUser user = new PantryUser(googleId);
		user.updatePrimaryEmail(email);
		String actual = user.getPrimaryEmail();
		assertThat(actual, is(email));
	}

}
