package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.wecancodeit.pantryplus2electricboogaloo.user.PantryUser;

@RestController
public class UserRestController extends LoginController {

	@PatchMapping(path = "/update-user-info", params = "firstName")
	public void receivePatchForFirstName(OAuth2AuthenticationToken token, @RequestParam String firstName) {
		PantryUser user = resolveUser(token);
		user.updateFirstName(firstName);
	}

	@PatchMapping(path = "/update-user-info", params = "lastName")
	public void receivePatchForLastName(OAuth2AuthenticationToken token, @RequestParam String lastName) {
		PantryUser user = resolveUser(token);
		user.updateLastName(lastName);
	}

	@PatchMapping(path = "/update-user-info", params = "address")
	public void receivePatchForAddress(OAuth2AuthenticationToken token, @RequestParam String address) {
		PantryUser user = resolveUser(token);
		user.updateAddress(address);
	}

	@PatchMapping(path = "/update-user-info", params = "familySize")
	public void receivePatchForFamilySize(OAuth2AuthenticationToken token, @RequestParam int familySize) {
		PantryUser user = resolveUser(token);
		user.updateFamilySize(familySize);
	}

}
