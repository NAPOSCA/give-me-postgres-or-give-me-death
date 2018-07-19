package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.wecancodeit.pantryplus2electricboogaloo.user.PantryUser;
import org.wecancodeit.pantryplus2electricboogaloo.user.UserRepository;

public abstract class LoginController {

	@Resource
	private UserRepository userRepo;

	@Resource
	private EntityManager entityManager;

	@Transactional
	public PantryUser resolveUser(OAuth2AuthenticationToken token) {
		String googleName = getGoogleNameFrom(token);
		return userRepo.findByGoogleName(googleName).orElseGet(() -> {
			OAuth2User googleId = getGoogleId(token);
			userRepo.save(new PantryUser(googleId));
			entityManager.flush();
			entityManager.clear();
			return userRepo.findByGoogleName(googleName).get();
		});
	}

	private String getGoogleNameFrom(OAuth2AuthenticationToken token) {
		return (String) getGoogleId(token).getAttributes().get("sub");
	}

	private OAuth2User getGoogleId(OAuth2AuthenticationToken token) {
		return token.getPrincipal();
	}

}
