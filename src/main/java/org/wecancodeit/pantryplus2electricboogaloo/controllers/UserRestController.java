package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.wecancodeit.pantryplus2electricboogaloo.user.PantryUser;

public class UserRestController extends LoginController {

	public void updateUsersFirstName(OAuth2AuthenticationToken token, String firstName) {
		PantryUser user = resolveUser(token);
		user.updateFirstName(firstName);
	}

	public void updateUsersLastName(OAuth2AuthenticationToken token, String lastName) {
		PantryUser user = resolveUser(token);
		user.updateLastName(lastName);
	}

}
