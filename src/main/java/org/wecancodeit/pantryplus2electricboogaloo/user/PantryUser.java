package org.wecancodeit.pantryplus2electricboogaloo.user;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.security.oauth2.core.user.OAuth2User;
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
	private String zipCode;
	private String referral;
	private boolean hasInfants;
	private String primaryPhoneNumber;
	private String primaryEmail;
	private String googleEmail;

	public PantryUser() {
	}

	public PantryUser(OAuth2User googleId) {
		Map<String, Object> attributes = googleId.getAttributes();
		googleName = (String) attributes.get("sub");
		firstName = (String) attributes.get("given_name");
		lastName = (String) attributes.get("family_name");
		googleEmail = (String) attributes.get("email");
		primaryEmail = this.googleEmail;
		schoolAgeChildren = -1;
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

	public String getPrimaryPhoneNumber() {
		return primaryPhoneNumber;
	}

	public String getReferral() {
		return referral;
	}

	public String getZipCode() {
		return zipCode;
	}

	public boolean getHasInfants() {
		return hasInfants;
	}

	public String getGoogleEmail() {
		return googleEmail;
	}

	public String getPrimaryEmail() {
		return primaryEmail;
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

	public void updateZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public void updateReferral(String referral) {
		this.referral = referral;
	}

	public void updateHasInfants(boolean hasInfants) {
		this.hasInfants = hasInfants;
	}

	public void updatePrimaryPhoneNumber(String primaryPhoneNumber) {
		this.primaryPhoneNumber = primaryPhoneNumber;
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
		if (getZipCode() == null) {
			return false;
		}
		if (getReferral() == null) {
			return false;
		}
		if (getPrimaryPhoneNumber() == null) {
			return false;
		}
		return true;
	}

}
