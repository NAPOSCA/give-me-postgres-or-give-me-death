package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wecancodeit.pantryplus2electricboogaloo.cart.Cart;
import org.wecancodeit.pantryplus2electricboogaloo.cart.CartRepository;
import org.wecancodeit.pantryplus2electricboogaloo.category.CategoryRepository;
import org.wecancodeit.pantryplus2electricboogaloo.user.User;
import org.wecancodeit.pantryplus2electricboogaloo.user.UserRepository;

@Controller
public class PantryController {

	@Resource
	private CategoryRepository categoryRepo;

	@Resource
	private UserRepository userRepo;

	@Resource
	private CartRepository cartRepo;

	@Resource
	private EntityManager entityManager;

	@RequestMapping("/")
	public String displayUserForm() {
		return "user-form";
	}

	@RequestMapping("/shopping")
	public String displayShopping(Model model, OAuth2AuthenticationToken token) {
		model.addAttribute("categories", categoryRepo.findAll());
		User user = getUser(token);
		Cart cart = user.getCart();
		model.addAttribute("cart", cart);
		return "shopping";
	}

	@RequestMapping("/cart")
	public String displayCart(Model model, OAuth2AuthenticationToken token) {
		User user = getUser(token);
		Cart cart = user.getCart();
		model.addAttribute("cart", cart);
		return "cart";
	}

	public User getUser(OAuth2AuthenticationToken token) {
		OAuth2User authenticatedUser = token.getPrincipal();
		Map<String, Object> principalAttributes = authenticatedUser.getAttributes();
		String googleName = (String) principalAttributes.get("sub");
		return userRepo.findByGoogleName(googleName).orElseGet(() -> {
			User other = new User(googleName);
			userRepo.save(other);
			entityManager.flush();
			entityManager.clear();
			return userRepo.findByGoogleName(googleName).get();
		});
	}

	@RequestMapping("/about-us")
	public String displayAboutUs() {
		return "about-us";
	}

}
