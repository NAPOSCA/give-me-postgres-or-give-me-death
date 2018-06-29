package org.wecancodeit.pantryplus2electricboogaloo.user;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

	@Id
	private long id;
	private int familySize;

	public User(String firstName, String lastName, int familySize, int schoolAgeChildren, boolean hasInfants,
			String pickupDate, String zipCode, String address, String birthdate) {
		this.familySize = familySize;
	}

	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int calculateCouponLimit() {
		if (familySize > 8) {
			return 35;
		}
		if (familySize > 6) {
			return 30;
		}
		if (familySize > 4) {
			return 25;
		}
		if (familySize > 2) {
			return 20;
		}
		return 10;
	}

}
