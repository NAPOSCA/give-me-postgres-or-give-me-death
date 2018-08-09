package org.wecancodeit.pantryplus2electricboogaloo;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.wecancodeit.pantryplus2electricboogaloo.cart.Cart;
import org.wecancodeit.pantryplus2electricboogaloo.cart.CartRepository;
import org.wecancodeit.pantryplus2electricboogaloo.user.PantryUser;
import org.wecancodeit.pantryplus2electricboogaloo.user.UserRepository;

@Service
public class LoginService {

	@Resource
	private UserRepository userRepo;

	@Resource
	private EntityManager entityManager;

	@Resource
	private CartRepository cartRepo;

	@Transactional
	public PantryUser resolveUser(OAuth2User googleId) {
		String googleName = googleId.getName();
		return userRepo.findByGoogleName(googleName).orElseGet(() -> {
			PantryUser user = userRepo.save(new PantryUser(googleId));
			cartRepo.save(new Cart(user));
			entityManager.flush();
			entityManager.clear();
			return userRepo.findByGoogleName(googleName).get();
		});
	}

	public boolean isAdmin(OAuth2User googleId) {
		return googleId.getName().equals("115969733168111031226");
	}

}
