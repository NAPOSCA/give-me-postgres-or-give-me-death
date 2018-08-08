package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

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
import org.wecancodeit.pantryplus2electricboogaloo.cart.CartRepository;
import org.wecancodeit.pantryplus2electricboogaloo.currency.Currency;
import org.wecancodeit.pantryplus2electricboogaloo.currency.CurrencyRepository;
import org.wecancodeit.pantryplus2electricboogaloo.lineitem.CountedLineItem;
import org.wecancodeit.pantryplus2electricboogaloo.lineitem.LineItem;
import org.wecancodeit.pantryplus2electricboogaloo.lineitem.LineItemRepository;
import org.wecancodeit.pantryplus2electricboogaloo.product.LimitedProduct;
import org.wecancodeit.pantryplus2electricboogaloo.product.PricedProduct;
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

	@Resource
	private EntityManager entityManager;

	@Resource
	private CartRepository cartRepo;

	@Resource
	private CurrencyRepository currencyRepo;

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

	@Transactional
	@PutMapping("/cart/products/{productId}")
	public Map<String, Object> receivePutOnProduct(@AuthenticationPrincipal OAuth2User googleId,
			@PathVariable long productId, @RequestParam int quantity) {
		Cart cart = loginService.resolveUser(googleId).getCart();
		LimitedProduct product = (LimitedProduct) productRepo.findById(productId).get();
		CountedLineItem lineItem = cart.getCountedLineItemContaining(productId)
				.orElseGet(() -> new CountedLineItem(cart, product));
		Map<String, Object> json = new HashMap<>();
		if (quantity <= 0) {
			lineItemRepo.delete(lineItem);
			json.put("quantity", 0);
		} else {
			int maxQuantity = product.getMaximumQuantity();
			if (quantity > maxQuantity) {
				quantity = maxQuantity;
			}
			lineItem.setQuantity(quantity);
			lineItem = lineItemRepo.save(lineItem);
			json.put("quantity", lineItem.getQuantity());
		}
		if (product.getType().equals("PricedProduct")) {
			PricedProduct pricedProduct = (PricedProduct) product;
			long currencyId = pricedProduct.getCurrency().getId();
			json.put("currencyId", currencyId);
			long cartId = cart.getId();
			entityManager.flush();
			entityManager.clear();
			Cart cartAgain = cartRepo.findById(cartId).get();
			Currency currency = currencyRepo.findById(currencyId).get();
			int amountUsed = cartAgain.amountUsed(currency);
			json.put("amountUsed", amountUsed);
		}
		return json;
	}

}
