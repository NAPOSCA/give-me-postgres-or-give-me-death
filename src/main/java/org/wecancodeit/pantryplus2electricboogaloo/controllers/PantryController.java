package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wecancodeit.pantryplus2electricboogaloo.cart.Cart;
import org.wecancodeit.pantryplus2electricboogaloo.cart.CartRepository;
import org.wecancodeit.pantryplus2electricboogaloo.category.CategoryRepository;
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
	public String displayUserForm(Model model, OAuth2AuthenticationToken token) {
		model.addAttribute("user", resolveUser(token));
		return "user-form";
	}

	@Transactional
	@RequestMapping("/shopping")
	public String displayShopping(Model model, OAuth2AuthenticationToken token) {
		model.addAttribute("categories", categoryRepo.findAll());
		model.addAttribute("cart", resolveUser(token).getCart());
		return "shopping";
	}

	@Transactional
	@RequestMapping("/cart")
	public String displayCart(Model model, OAuth2AuthenticationToken token) {
		Cart cart = resolveUser(token).getCart();
		model.addAttribute("cart", cart);
		model.addAttribute("lineItems", cart.getLineItems());
		model.addAttribute("countedLineItems", cart.getCountedLineItems());
		return "cart";
	}

	@RequestMapping("/about-us")
	public String displayAboutUs() {
		return "about-us";
	}

	@RequestMapping("/")
	public String displayWelcomeView(Model model, OAuth2AuthenticationToken token) {
		model.addAttribute("authenticated", token.isAuthenticated());
		return "welcome";
	}

}
