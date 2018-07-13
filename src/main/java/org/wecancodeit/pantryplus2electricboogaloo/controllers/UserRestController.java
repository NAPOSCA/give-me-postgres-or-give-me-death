package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.PATCH;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.wecancodeit.pantryplus2electricboogaloo.user.PantryUser;

public class UserRestController extends LoginController {

	@RequestMapping(path = "/update-user-info", params = "firstName", method = PATCH)
	public void receivePatchForFirstName(OAuth2AuthenticationToken token, @RequestParam String firstName) {
		PantryUser user = resolveUser(token);
		user.updateFirstName(firstName);
	}

	@RequestMapping(path = "/update-user-info", params = "lastName", method = PATCH)
	public void receivePatchForLastName(OAuth2AuthenticationToken token, @RequestParam String lastName) {
		PantryUser user = resolveUser(token);
		user.updateLastName(lastName);
	}

	@RequestMapping(path = "/update-user-info", params = "address", method = PATCH)
	public void receivePatchForAddress(OAuth2AuthenticationToken token, @RequestParam String address) {
		PantryUser user = resolveUser(token);
		user.updateAddress(address);
	}

}
