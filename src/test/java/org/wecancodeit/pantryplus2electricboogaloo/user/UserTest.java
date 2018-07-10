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
		User user = new User(googleName);
		user.updateFamilySize(familySize);
		int couponLimit = user.calculateCouponLimit();
		assertThat(couponLimit, is(10));
	}

	@Test
	public void shouldCalculateCouponTotalForTwoPersonFamily() {
		familySize = 2;
		User user = new User(googleName);
		user.updateFamilySize(familySize);
		int couponLimit = user.calculateCouponLimit();
		assertThat(couponLimit, is(10));
	}

	@Test
	public void shouldCalculateCouponTotalForThreePersonFamily() {
		familySize = 3;
		User user = new User(googleName);
		user.updateFamilySize(familySize);
		int couponLimit = user.calculateCouponLimit();
		assertThat(couponLimit, is(20));
	}

	@Test
	public void shouldCalculateCouponTotalForFourPersonFamily() {
		familySize = 4;
		User user = new User(googleName);
		user.updateFamilySize(familySize);
		int couponLimit = user.calculateCouponLimit();
		assertThat(couponLimit, is(20));
	}

	@Test
	public void shouldCalculateCouponTotalForFivePersonFamily() {
		familySize = 5;
		User user = new User(googleName);
		user.updateFamilySize(familySize);
		int couponLimit = user.calculateCouponLimit();
		assertThat(couponLimit, is(25));
	}

	@Test
	public void shouldCalculateCouponTotalForSixPersonFamily() {
		familySize = 6;
		User user = new User(googleName);
		user.updateFamilySize(familySize);
		int couponLimit = user.calculateCouponLimit();
		assertThat(couponLimit, is(25));
	}

	@Test
	public void shouldCalculateCouponTotalForSevenPersonFamily() {
		familySize = 7;
		User user = new User(googleName);
		user.updateFamilySize(familySize);
		int couponLimit = user.calculateCouponLimit();
		assertThat(couponLimit, is(30));
	}

	@Test
	public void shouldCalculateCouponTotalForEightPersonFamily() {
		familySize = 8;
		User user = new User(googleName);
		user.updateFamilySize(familySize);
		int couponLimit = user.calculateCouponLimit();
		assertThat(couponLimit, is(30));
	}

	@Test
	public void shouldCalculateCouponTotalForNinePersonFamily() {
		familySize = 9;
		User user = new User(googleName);
		user.updateFamilySize(familySize);
		int couponLimit = user.calculateCouponLimit();
		assertThat(couponLimit, is(35));
	}

	@Test
	public void shouldCalculateMeatLimitForOnePerson() {
		familySize = 1;
		User user = new User(googleName);
		user.updateFamilySize(familySize);
		int meatLimit = user.calculateMeatLimit();
		assertThat(meatLimit, is(4));
	}

	@Test
	public void shouldCalculateMeatLimitForTwoPersonFamily() {
		familySize = 2;
		User user = new User(googleName);
		user.updateFamilySize(familySize);
		int meatLimit = user.calculateMeatLimit();
		assertThat(meatLimit, is(4));
	}

	@Test
	public void shouldCalculateMeatLimitForThreePersonFamily() {
		familySize = 3;
		User user = new User(googleName);
		user.updateFamilySize(familySize);
		int meatLimit = user.calculateMeatLimit();
		assertThat(meatLimit, is(6));
	}

	@Test
	public void shouldCalculateMeatLimitForFourPersonFamily() {
		familySize = 4;
		User user = new User(googleName);
		user.updateFamilySize(familySize);
		int meatLimit = user.calculateMeatLimit();
		assertThat(meatLimit, is(6));
	}

	@Test
	public void shouldCalculateMeatLimitForFivePersonFamily() {
		familySize = 5;
		User user = new User(googleName);
		user.updateFamilySize(familySize);
		int meatLimit = user.calculateMeatLimit();
		assertThat(meatLimit, is(6));
	}

	@Test
	public void shouldCalculateMeatLimitForSixPersonFamily() {
		familySize = 6;
		User user = new User(googleName);
		user.updateFamilySize(familySize);
		int meatLimit = user.calculateMeatLimit();
		assertThat(meatLimit, is(8));
	}

}
