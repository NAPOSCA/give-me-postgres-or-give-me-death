package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.wecancodeit.pantryplus2electricboogaloo.user.PantryUser;

@RestController
public class UserRestController extends LoginController {

	@PatchMapping(path = "/user", params = "firstName")
	public String receivePatchForFirstName(OAuth2AuthenticationToken token, @RequestParam String firstName) {
		PantryUser user = resolveUser(token);
		user.updateFirstName(firstName);
		return firstName;
	}

	@PatchMapping(path = "/user", params = "lastName")
	public String receivePatchForLastName(OAuth2AuthenticationToken token, @RequestParam String lastName) {
		PantryUser user = resolveUser(token);
		user.updateLastName(lastName);
		return lastName;
	}

	@PatchMapping(path = "/user", params = "address")
	public String receivePatchForAddress(OAuth2AuthenticationToken token, @RequestParam String address) {
		PantryUser user = resolveUser(token);
		user.updateAddress(address);
		return address;
	}

	@PatchMapping(path = "/user", params = "familySize")
	public int receivePatchForFamilySize(OAuth2AuthenticationToken token, @RequestParam int familySize) {
		PantryUser user = resolveUser(token);
		user.updateFamilySize(familySize);
		return familySize;
	}

	@PatchMapping(path = "/user", params = "birthDate")
	public String receivePatchForBirthDate(OAuth2AuthenticationToken token, @RequestParam String birthDate) {
		PantryUser user = resolveUser(token);
		user.updateBirthdate(birthDate);
		return birthDate;
	}

	@PatchMapping(path = "/user", params = "schoolAgeChildren")
	public int receivePatchForSchoolAgeChildren(OAuth2AuthenticationToken token, @RequestParam int schoolAgeChildren) {
		PantryUser user = resolveUser(token);
		user.updateSchoolAgeChildren(schoolAgeChildren);
		return schoolAgeChildren;
	}

	@PostMapping(path = "/user")
	public PantryUser receivePostOnUser(OAuth2AuthenticationToken token, @RequestParam String firstName,
			@RequestParam String lastName, @RequestParam String address, @RequestParam int familySize,
			@RequestParam String birthdate) {
		PantryUser user = resolveUser(token);
		user.updateFirstName(firstName);
		user.updateLastName(lastName);
		user.updateAddress(address);
		user.updateFamilySize(familySize);
		user.updateBirthdate(birthdate);
		return user;
	}

}
