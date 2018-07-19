package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.wecancodeit.pantryplus2electricboogaloo.user.PantryUser;
import org.wecancodeit.pantryplus2electricboogaloo.user.UserRepository;

public abstract class LoginController {
	
	@Resource
	private UserRepository userRepo;
	
	@Resource
	private EntityManager entityManager;
	
	public PantryUser resolveUser(OAuth2AuthenticationToken token) {
		String googleName = getGoogleNameFrom(token);
		return userRepo.findByGoogleName(googleName).orElseGet(() -> {
			userRepo.save(new PantryUser(googleName));
			entityManager.flush();
			entityManager.clear();
			return userRepo.findByGoogleName(googleName).get();
		});
	}

	private String getGoogleNameFrom(OAuth2AuthenticationToken token) {
		return (String) token.getPrincipal().getAttributes().get("sub");
	}
	
}
