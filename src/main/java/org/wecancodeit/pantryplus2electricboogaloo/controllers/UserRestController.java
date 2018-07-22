package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.wecancodeit.pantryplus2electricboogaloo.user.PantryUser;

@RestController
public class UserRestController extends LoginController {

	@PatchMapping(path = "/user", params = "firstName")
	public String receivePatchForFirstName(@AuthenticationPrincipal OAuth2User googleId, @RequestParam String firstName) {
		PantryUser user = resolveUser(googleId);
		user.updateFirstName(firstName);
		return firstName;
	}

	@PatchMapping(path = "/user", params = "lastName")
	public String receivePatchForLastName(@AuthenticationPrincipal OAuth2User googleId, @RequestParam String lastName) {
		PantryUser user = resolveUser(googleId);
		user.updateLastName(lastName);
		return lastName;
	}

	@PatchMapping(path = "/user", params = "address")
	public String receivePatchForAddress(@AuthenticationPrincipal OAuth2User googleId, @RequestParam String address) {
		PantryUser user = resolveUser(googleId);
		user.updateAddress(address);
		return address;
	}

	@PatchMapping(path = "/user", params = "familySize")
	public int receivePatchForFamilySize(@AuthenticationPrincipal OAuth2User googleId, @RequestParam int familySize) {
		PantryUser user = resolveUser(googleId);
		user.updateFamilySize(familySize);
		return familySize;
	}

	@PatchMapping(path = "/user", params = "birthDate")
	public String receivePatchForBirthDate(@AuthenticationPrincipal OAuth2User googleId, @RequestParam String birthDate) {
		PantryUser user = resolveUser(googleId);
		user.updateBirthdate(birthDate);
		return birthDate;
	}

	@PatchMapping(path = "/user", params = "schoolAgeChildren")
	public int receivePatchForSchoolAgeChildren(@AuthenticationPrincipal OAuth2User googleId, @RequestParam int schoolAgeChildren) {
		PantryUser user = resolveUser(googleId);
		user.updateSchoolAgeChildren(schoolAgeChildren);
		return schoolAgeChildren;
	}

}
