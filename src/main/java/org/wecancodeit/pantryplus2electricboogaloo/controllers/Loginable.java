package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import javax.persistence.EntityManager;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.wecancodeit.pantryplus2electricboogaloo.user.User;
import org.wecancodeit.pantryplus2electricboogaloo.user.UserRepository;

public interface Loginable {
	
	public default User resolveUser(OAuth2AuthenticationToken token, UserRepository userRepo, EntityManager entityManager) {
		String googleName = (String) token.getPrincipal().getAttributes().get("sub");
		return userRepo.findByGoogleName(googleName).orElseGet(() -> {
			userRepo.save(new User(googleName));
			entityManager.flush();
			entityManager.clear();
			return userRepo.findByGoogleName(googleName).get();
		});
	}
	
}
