package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wecancodeit.pantryplus2electricboogaloo.cart.Cart;
import org.wecancodeit.pantryplus2electricboogaloo.cart.CartRepository;
import org.wecancodeit.pantryplus2electricboogaloo.category.CategoryRepository;
import org.wecancodeit.pantryplus2electricboogaloo.user.PantryUser;
import org.wecancodeit.pantryplus2electricboogaloo.user.UserRepository;

@Controller
public class PantryController extends LoginController {

	@Resource
	private CategoryRepository categoryRepo;

	@Resource
	private UserRepository userRepo;

	@Resource
	private CartRepository cartRepo;

	@Resource
	private EntityManager entityManager;

	@Transactional
	@RequestMapping("/settings")
	public String displayUserForm(Model model, @AuthenticationPrincipal OAuth2User googleId) {
		model.addAttribute("user", resolveUser(googleId));
		return "user-form";
	}

	@Transactional
	@RequestMapping("/shopping")
	public String displayShopping(Model model, @AuthenticationPrincipal OAuth2User googleId) {
		model.addAttribute("categories", categoryRepo.findAll());
		PantryUser user = resolveUser(googleId);
		if(!user.isValid()) {
			return "redirect:/settings";
		}
		model.addAttribute("cart", user.getCart());
		
		return "shopping";
	}

	@Transactional
	@RequestMapping("/cart")
	public String displayCart(Model model, @AuthenticationPrincipal OAuth2User googleId) {
		Cart cart = resolveUser(googleId).getCart();
		model.addAttribute("cart", cart);
		model.addAttribute("lineItems", cart.getLineItems());
		model.addAttribute("countedLineItems", cart.getCountedLineItems());
		return "cart";
	}

	@RequestMapping("/about-us")
	public String displayAboutUs() {
		return "about-us";
	}

	@Transactional
	@RequestMapping("/")
	public String displayWelcomeView(Model model, @AuthenticationPrincipal OAuth2User googleId) {
		boolean isAuthenticated = googleId != null;
		model.addAttribute("authenticated", isAuthenticated);
		if (isAuthenticated) {
			PantryUser user = resolveUser(googleId);
			if(!user.isValid()) {
				return "redirect:/settings";
			}
			model.addAttribute("user", user);
		}
		return "welcome";
	}

}
