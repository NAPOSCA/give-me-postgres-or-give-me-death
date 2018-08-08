package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.wecancodeit.pantryplus2electricboogaloo.LoginService;
import org.wecancodeit.pantryplus2electricboogaloo.cart.Cart;
import org.wecancodeit.pantryplus2electricboogaloo.lineitem.CountedLineItem;
import org.wecancodeit.pantryplus2electricboogaloo.lineitem.LineItem;
import org.wecancodeit.pantryplus2electricboogaloo.lineitem.LineItemRepository;
import org.wecancodeit.pantryplus2electricboogaloo.product.LimitedProduct;
import org.wecancodeit.pantryplus2electricboogaloo.product.Product;
import org.wecancodeit.pantryplus2electricboogaloo.product.ProductRepository;
import org.wecancodeit.pantryplus2electricboogaloo.user.PantryUser;

@RestController
public class CartRestController {

	@Resource
	private LoginService loginService;

	@Resource
	private ProductRepository productRepo;

	@Resource
	private LineItemRepository lineItemRepo;

	@PostMapping("/cart/products/{productId}")
	public void receivePostOnProduct(@AuthenticationPrincipal OAuth2User googleId, @PathVariable long productId) {
		PantryUser user = loginService.resolveUser(googleId);
		Cart cart = user.getCart();
		Product product = productRepo.findById(productId).get();
		if (!cart.has(product)) {
			LineItem lineItem = new LineItem(cart, product);
			lineItemRepo.save(lineItem);
		}
	}

	@DeleteMapping("/cart/products/{productId}")
	public void receiveDeleteOnProduct(@AuthenticationPrincipal OAuth2User googleId, @PathVariable long productId) {
		PantryUser user = loginService.resolveUser(googleId);
		Cart cart = user.getCart();
		Optional<LineItem> potentialLineItem = cart.getLineItemContaining(productId);
		if (potentialLineItem.isPresent()) {
			lineItemRepo.delete(potentialLineItem.get());
		}
	}

	@PutMapping("/cart/products/{productId}")
	public int receivePutOnProduct(@AuthenticationPrincipal OAuth2User googleId, @PathVariable long productId,
			@RequestParam int quantity) {
		Cart cart = loginService.resolveUser(googleId).getCart();
		LimitedProduct product = (LimitedProduct) productRepo.findById(productId).get();
		CountedLineItem lineItem = cart.getCountedLineItemContaining(productId)
				.orElseGet(() -> new CountedLineItem(cart, product));
		if (quantity <= 0) {
			lineItemRepo.delete(lineItem);
			return 0;
		}
		int maxQuantity = product.getMaximumQuantity();
		if (quantity > maxQuantity) {
			quantity = maxQuantity;
		}
		lineItem.setQuantity(quantity);
		lineItem = lineItemRepo.save(lineItem);
		return lineItem.getQuantity();
	}

}
