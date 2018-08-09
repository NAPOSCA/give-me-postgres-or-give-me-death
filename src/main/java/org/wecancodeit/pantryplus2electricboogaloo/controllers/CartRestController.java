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
		PantryUser user = loginService.resolveUser(googleId);
		Cart cart = user.getCart();
		LimitedProduct product = (LimitedProduct) productRepo.findById(productId).get();
		CountedLineItem lineItem = cart.getCountedLineItemContaining(productId)
				.orElseGet(() -> new CountedLineItem(user.getCart(), product));
		if (quantity <= 0) {
			lineItemRepo.delete(lineItem);
			entityManager.flush();
			entityManager.clear();
			cart = cartRepo.findById(cart.getId()).get();
		} else if (cart.canSetQuantityOfProductTo(quantity, product)) {
			lineItem.setQuantity(quantity);
			lineItemRepo.save(lineItem);
			entityManager.flush();
			entityManager.clear();
			cart = cartRepo.findById(cart.getId()).get();
		}
		Map<String, Object> json = new HashMap<>();
		json.put("quantity", cart.getQuantityOf(productId));
		if (product instanceof PricedProduct) {
			PricedProduct pricedProduct = (PricedProduct) product;
			Currency currency = pricedProduct.getCurrency();
			json.put("currencyId", currency.getId());
			json.put("amountUsed", cart.allowanceUsed(currency));
		}
		return json;
	}

}
