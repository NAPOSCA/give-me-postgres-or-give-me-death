package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.wecancodeit.pantryplus2electricboogaloo.user.PantryUser;

@Controller
public class UserController extends LoginController {

	public String receivePostOnUser(OAuth2AuthenticationToken token, @RequestParam String firstName,
			@RequestParam String lastName, @RequestParam String address, @RequestParam int familySize,
			@RequestParam String birthdate) {
		PantryUser user = resolveUser(token);
		user.updateFirstName(firstName);
		user.updateLastName(lastName);
		user.updateAddress(address);
		user.updateFamilySize(familySize);
		user.updateBirthdate(birthdate);
		return "redirect:/settings";
	}
}
