package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import static java.util.stream.Collectors.toList;

import java.util.Collection;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wecancodeit.pantryplus2electricboogaloo.cart.Cart;
import org.wecancodeit.pantryplus2electricboogaloo.cart.CartRepository;
import org.wecancodeit.pantryplus2electricboogaloo.category.CategoryRepository;
import org.wecancodeit.pantryplus2electricboogaloo.lineitem.CountedLineItem;
import org.wecancodeit.pantryplus2electricboogaloo.lineitem.LineItem;
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
		Cart cart = getUser(token).getCart();
		model.addAttribute("cart", cart);
		return "shopping";
	}

	@RequestMapping("/cart")
	public String displayCart(Model model, OAuth2AuthenticationToken token) {
		Cart cart = getUser(token).getCart();
		model.addAttribute("cart", cart);
		Collection<LineItem> allLineItems = cart.getLineItems();
		Collection<LineItem> lineItems = allLineItems.stream().filter(item -> !isCountedLineItem(item))
				.collect(toList());
		model.addAttribute("lineItems", lineItems);
		Collection<CountedLineItem> countedLineItems = allLineItems.stream().filter(item -> isCountedLineItem(item))
				.map(item -> (CountedLineItem) item).collect(toList());
		model.addAttribute("countedLineItems", countedLineItems);
		return "cart";
	}

	private boolean isCountedLineItem(LineItem item) {
		return item instanceof CountedLineItem;
	}

	@RequestMapping("/about-us")
	public String displayAboutUs() {
		return "about-us";
	}

	private User getUser(OAuth2AuthenticationToken token) {
		String googleName = (String) token.getPrincipal().getAttributes().get("sub");
		return userRepo.findByGoogleName(googleName).orElseGet(() -> {
			userRepo.save(new User(googleName));
			entityManager.flush();
			entityManager.clear();
			return userRepo.findByGoogleName(googleName).get();
		});
	}

}
