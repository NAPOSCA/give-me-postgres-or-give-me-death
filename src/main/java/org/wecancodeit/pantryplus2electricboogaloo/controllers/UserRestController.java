package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.wecancodeit.pantryplus2electricboogaloo.user.PantryUser;

@RestController
public class UserRestController extends LoginController {

	@PatchMapping(path = "/update-user-info", params = "firstName")
	public String receivePatchForFirstName(OAuth2AuthenticationToken token, @RequestParam String firstName) {
		PantryUser user = resolveUser(token);
		user.updateFirstName(firstName);
		return firstName;
	}

	@PatchMapping(path = "/update-user-info", params = "lastName")
	public String receivePatchForLastName(OAuth2AuthenticationToken token, @RequestParam String lastName) {
		PantryUser user = resolveUser(token);
		user.updateLastName(lastName);
		return lastName;
	}

	@PatchMapping(path = "/update-user-info", params = "address")
	public String receivePatchForAddress(OAuth2AuthenticationToken token, @RequestParam String address) {
		PantryUser user = resolveUser(token);
		user.updateAddress(address);
		return address;
	}

	@PatchMapping(path = "/update-user-info", params = "familySize")
	public int receivePatchForFamilySize(OAuth2AuthenticationToken token, @RequestParam int familySize) {
		PantryUser user = resolveUser(token);
		user.updateFamilySize(familySize);
		return familySize;
	}

	@PatchMapping(path = "/update-user-info", params = "birthDate")
	public String receivePatchForBirthDate(OAuth2AuthenticationToken token, @RequestParam String birthDate) {
		PantryUser user = resolveUser(token);
		user.updateBirthDate(birthDate);
		return birthDate;
	}

	@PatchMapping(path = "/update-user-info", params = "schoolAgeChildren")
	public int receivePatchForSchoolAgeChildren(OAuth2AuthenticationToken token, @RequestParam int schoolAgeChildren) {
		PantryUser user = resolveUser(token);
		user.updateSchoolAgeChildren(schoolAgeChildren);
		return schoolAgeChildren;
	}

}
