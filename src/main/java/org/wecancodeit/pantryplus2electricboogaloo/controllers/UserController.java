package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import javax.annotation.Resource;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.wecancodeit.pantryplus2electricboogaloo.LoginService;
import org.wecancodeit.pantryplus2electricboogaloo.user.PantryUser;
import org.wecancodeit.pantryplus2electricboogaloo.user.UserRepository;

@Controller
public class UserController {

	@Resource
	private UserRepository userRepo;
	
	@Resource
	private LoginService loginService;

	@RequestMapping("/user")
	public String receiveRequestOnUser(@AuthenticationPrincipal OAuth2User googleId, @RequestParam String firstName,
			@RequestParam String lastName, @RequestParam String address, @RequestParam int familySize,
			@RequestParam String birthdate, @RequestParam int schoolAgeChildren, @RequestParam String zipCode,
			@RequestParam String referral, @RequestParam(required = false) boolean hasInfants,
			@RequestParam String primaryPhoneNumber, @RequestParam String secondaryPhoneNumber,
			@RequestParam String primaryEmail) {
		PantryUser user = loginService.resolveUser(googleId);
		user.updateFirstName(firstName);
		user.updateLastName(lastName);
		user.updateAddress(address);
		user.updateFamilySize(familySize);
		user.updateBirthdate(birthdate);
		if (familySize >= schoolAgeChildren) {
			user.updateSchoolAgeChildren(schoolAgeChildren);
		}
		user.updateZipCode(zipCode);
		user.updateReferral(referral);
		user.updateHasInfants(hasInfants);
		user.updatePrimaryPhoneNumber(primaryPhoneNumber);
		user.updateSecondaryPhoneNumber(secondaryPhoneNumber);
		user.updatePrimaryEmail(primaryEmail);
		userRepo.save(user);
		return "redirect:/settings";
	}
}
