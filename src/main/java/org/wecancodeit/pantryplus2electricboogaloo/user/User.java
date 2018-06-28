package org.wecancodeit.pantryplus2electricboogaloo.user;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
	
	@Id
	private long id;

	public User(String firstName, String lastName, int familySize, int schoolAgeChildren, boolean hasInfants,
			String pickupDateString, String zipCode, String address, String birthdate) {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

}
