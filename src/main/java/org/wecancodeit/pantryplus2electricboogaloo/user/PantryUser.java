package org.wecancodeit.pantryplus2electricboogaloo.user;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.wecancodeit.pantryplus2electricboogaloo.cart.Cart;

@Entity
public class PantryUser {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private long id;
	private int familySize;
	@OneToOne(mappedBy = "user")
	private Cart cart;

	private String googleName;
	private String firstName;
	private String lastName;
	private String birthdate;
	private String address;
	private int schoolAgeChildren;

	public PantryUser() {
	}

	public PantryUser(String googleName) {
		this.googleName = googleName;
		this.schoolAgeChildren = -1;
	}

	public long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Cart getCart() {
		return cart;
	}

	public String getGoogleName() {
		return googleName;
	}

	public int getFamilySize() {
		return familySize;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public String getAddress() {
		return address;
	}

	public int getSchoolAgeChildren() {
		return schoolAgeChildren;
	}

	public void updateFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void updateLastName(String lastName) {
		this.lastName = lastName;
	}

	public void updateFamilySize(int familySize) {
		this.familySize = familySize;
	}

	public void updateBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public void updateAddress(String address) {
		this.address = address;
	}

	public void updateSchoolAgeChildren(int schoolAgeChildren) {
		this.schoolAgeChildren = schoolAgeChildren;
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

	public int calculateMeatLimit() {
		if (familySize > 5) {
			return 8;
		}
		if (familySize > 2) {
			return 6;
		}
		return 4;
	}

	public Map<String, Object> toModel() {
		Map<String, Object> model = new HashMap<>();
		model.put("firstName", getFirstName());
		model.put("lastName", getLastName());
		model.put("familySize", getFamilySize());
		model.put("birthDate", getBirthdate());
		model.put("address", getAddress());
		model.put("SchoolAgeChildren", getSchoolAgeChildren());
		return model;
	}

	public boolean isValid() {
		if (getAddress() == null) {
			return false;
		}
		if (getBirthdate() == null) {
			return false;
		}
		if (getFamilySize() == 0) {
			return false;
		}
		if (getFirstName() == null) {
			return false;
		}
		if (getLastName() == null) {
			return false;
		}
		if (getSchoolAgeChildren() == -1) {
			return false;
		}
		if (getAddress().equals("")) {
			return false;
		}
		if (getBirthdate().equals("")) {
			return false;
		}
		if (getFirstName().equals("")) {
			return false;
		}
		if (getLastName().equals("")) {
			return false;
		}
		return true;
	}

}
