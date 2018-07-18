package org.wecancodeit.pantryplus2electricboogaloo.user;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class UserTest {

	int familySize;
	String googleName = "12345";

	@Test
	public void shouldCalculateCouponTotalForOnePerson() {
		familySize = 1;
		PantryUser user = new PantryUser(googleName);
		user.updateFamilySize(familySize);
		int couponLimit = user.calculateCouponLimit();
		assertThat(couponLimit, is(10));
	}

	@Test
	public void shouldCalculateCouponTotalForTwoPersonFamily() {
		familySize = 2;
		PantryUser user = new PantryUser(googleName);
		user.updateFamilySize(familySize);
		int couponLimit = user.calculateCouponLimit();
		assertThat(couponLimit, is(10));
	}

	@Test
	public void shouldCalculateCouponTotalForThreePersonFamily() {
		familySize = 3;
		PantryUser user = new PantryUser(googleName);
		user.updateFamilySize(familySize);
		int couponLimit = user.calculateCouponLimit();
		assertThat(couponLimit, is(20));
	}

	@Test
	public void shouldCalculateCouponTotalForFourPersonFamily() {
		familySize = 4;
		PantryUser user = new PantryUser(googleName);
		user.updateFamilySize(familySize);
		int couponLimit = user.calculateCouponLimit();
		assertThat(couponLimit, is(20));
	}

	@Test
	public void shouldCalculateCouponTotalForFivePersonFamily() {
		familySize = 5;
		PantryUser user = new PantryUser(googleName);
		user.updateFamilySize(familySize);
		int couponLimit = user.calculateCouponLimit();
		assertThat(couponLimit, is(25));
	}

	@Test
	public void shouldCalculateCouponTotalForSixPersonFamily() {
		familySize = 6;
		PantryUser user = new PantryUser(googleName);
		user.updateFamilySize(familySize);
		int couponLimit = user.calculateCouponLimit();
		assertThat(couponLimit, is(25));
	}

	@Test
	public void shouldCalculateCouponTotalForSevenPersonFamily() {
		familySize = 7;
		PantryUser user = new PantryUser(googleName);
		user.updateFamilySize(familySize);
		int couponLimit = user.calculateCouponLimit();
		assertThat(couponLimit, is(30));
	}

	@Test
	public void shouldCalculateCouponTotalForEightPersonFamily() {
		familySize = 8;
		PantryUser user = new PantryUser(googleName);
		user.updateFamilySize(familySize);
		int couponLimit = user.calculateCouponLimit();
		assertThat(couponLimit, is(30));
	}

	@Test
	public void shouldCalculateCouponTotalForNinePersonFamily() {
		familySize = 9;
		PantryUser user = new PantryUser(googleName);
		user.updateFamilySize(familySize);
		int couponLimit = user.calculateCouponLimit();
		assertThat(couponLimit, is(35));
	}

	@Test
	public void shouldCalculateMeatLimitForOnePerson() {
		familySize = 1;
		PantryUser user = new PantryUser(googleName);
		user.updateFamilySize(familySize);
		int meatLimit = user.calculateMeatLimit();
		assertThat(meatLimit, is(4));
	}

	@Test
	public void shouldCalculateMeatLimitForTwoPersonFamily() {
		familySize = 2;
		PantryUser user = new PantryUser(googleName);
		user.updateFamilySize(familySize);
		int meatLimit = user.calculateMeatLimit();
		assertThat(meatLimit, is(4));
	}

	@Test
	public void shouldCalculateMeatLimitForThreePersonFamily() {
		familySize = 3;
		PantryUser user = new PantryUser(googleName);
		user.updateFamilySize(familySize);
		int meatLimit = user.calculateMeatLimit();
		assertThat(meatLimit, is(6));
	}

	@Test
	public void shouldCalculateMeatLimitForFourPersonFamily() {
		familySize = 4;
		PantryUser user = new PantryUser(googleName);
		user.updateFamilySize(familySize);
		int meatLimit = user.calculateMeatLimit();
		assertThat(meatLimit, is(6));
	}

	@Test
	public void shouldCalculateMeatLimitForFivePersonFamily() {
		familySize = 5;
		PantryUser user = new PantryUser(googleName);
		user.updateFamilySize(familySize);
		int meatLimit = user.calculateMeatLimit();
		assertThat(meatLimit, is(6));
	}

	@Test
	public void shouldCalculateMeatLimitForSixPersonFamily() {
		familySize = 6;
		PantryUser user = new PantryUser(googleName);
		user.updateFamilySize(familySize);
		int meatLimit = user.calculateMeatLimit();
		assertThat(meatLimit, is(8));
	}

	@Test
	public void shouldBeValidUserIfAllInformationIsPresent() {
		PantryUser user = new PantryUser(googleName);
		user.updateAddress("1234 Main St");
		user.updateBirthdate("January 30th 1902");
		user.updateFamilySize(2);
		user.updateFirstName("First");
		user.updateLastName("Last");
		user.updateSchoolAgeChildren(0);
		boolean isValid = user.isValid();
		assertThat(isValid, is(true));
	}

	@Test
	public void shouldNotBeValidUserIfAddressIsMissing() {
		PantryUser user = new PantryUser(googleName);
		user.updateBirthdate("January 30th 1902");
		user.updateFamilySize(2);
		user.updateFirstName("First");
		user.updateLastName("Last");
		user.updateSchoolAgeChildren(0);
		boolean isValid = user.isValid();
		assertThat(isValid, is(false));
	}

	@Test
	public void shouldNotBeValidUserIfBirthDateIsMissing() {
		PantryUser user = new PantryUser(googleName);
		user.updateAddress("1234 Main St");
		user.updateFamilySize(2);
		user.updateFirstName("First");
		user.updateLastName("Last");
		user.updateSchoolAgeChildren(0);
		boolean isValid = user.isValid();
		assertThat(isValid, is(false));
	}

	@Test
	public void shouldNotBeValidUserIfFamilySizeIsMissing() {
		PantryUser user = new PantryUser(googleName);
		user.updateAddress("1234 Main St");
		user.updateBirthdate("January 30th 1902");
		user.updateFirstName("First");
		user.updateLastName("Last");
		user.updateSchoolAgeChildren(0);
		boolean isValid = user.isValid();
		assertThat(isValid, is(false));
	}

	@Test
	public void shouldNotBeValidUserIfFirstNameIsMissing() {
		PantryUser user = new PantryUser(googleName);
		user.updateAddress("1234 Main St");
		user.updateBirthdate("January 30th 1902");
		user.updateFamilySize(2);
		user.updateLastName("Last");
		user.updateSchoolAgeChildren(0);
		boolean isValid = user.isValid();
		assertThat(isValid, is(false));
	}

	@Test
	public void shouldNotBeValidUserIfLastNameIsMissing() {
		PantryUser user = new PantryUser(googleName);
		user.updateAddress("1234 Main St");
		user.updateBirthdate("January 30th 1902");
		user.updateFamilySize(2);
		user.updateFirstName("First");
		user.updateSchoolAgeChildren(0);
		boolean isValid = user.isValid();
		assertThat(isValid, is(false));
	}

	@Test
	public void shouldNotBeValidUserIfSchoolAgeChildrenIsMissing() {
		PantryUser user = new PantryUser(googleName);
		user.updateAddress("1234 Main St");
		user.updateBirthdate("January 30th 1902");
		user.updateFamilySize(2);
		user.updateFirstName("First");
		user.updateLastName("Last");
		boolean isValid = user.isValid();
		assertThat(isValid, is(false));
	}

	@Test
	public void shouldNotBeValidIfAddressIsBlank() {
		PantryUser user = new PantryUser(googleName);
		user.updateAddress("");
		user.updateBirthdate("January 30th 1902");
		user.updateFamilySize(2);
		user.updateFirstName("First");
		user.updateLastName("Last");
		user.updateSchoolAgeChildren(0);
		boolean isValid = user.isValid();
		assertThat(isValid, is(false));
	}

	@Test
	public void shouldNotBeValidIfBirthDateIsBlank() {
		PantryUser user = new PantryUser(googleName);
		user.updateAddress("1234 Main St");
		user.updateBirthdate("");
		user.updateFamilySize(2);
		user.updateFirstName("First");
		user.updateLastName("Last");
		user.updateSchoolAgeChildren(0);
		boolean isValid = user.isValid();
		assertThat(isValid, is(false));
	}

	@Test
	public void shouldNotBeValidIfFamilySizeIsZero() {
		PantryUser user = new PantryUser(googleName);
		user.updateAddress("1234 Main St");
		user.updateBirthdate("January 30th 1902");
		user.updateFamilySize(0);
		user.updateFirstName("First");
		user.updateLastName("Last");
		user.updateSchoolAgeChildren(0);
		boolean isValid = user.isValid();
		assertThat(isValid, is(false));
	}

	@Test
	public void shouldNotBeValidIfFirstNameIsBlank() {
		PantryUser user = new PantryUser(googleName);
		user.updateAddress("1234 Main St");
		user.updateBirthdate("January 30th 1902");
		user.updateFamilySize(2);
		user.updateFirstName("");
		user.updateLastName("Last");
		user.updateSchoolAgeChildren(0);
		boolean isValid = user.isValid();
		assertThat(isValid, is(false));
	}

	@Test
	public void shouldNotBeValidIfLastNameIsBlank() {
		PantryUser user = new PantryUser(googleName);
		user.updateAddress("1234 Main St");
		user.updateBirthdate("January 30th 1902");
		user.updateFamilySize(2);
		user.updateFirstName("First");
		user.updateLastName("");
		user.updateSchoolAgeChildren(0);
		boolean isValid = user.isValid();
		assertThat(isValid, is(false));
	}

}
