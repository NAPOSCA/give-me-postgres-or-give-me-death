package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import javax.annotation.Resource;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.wecancodeit.pantryplus2electricboogaloo.LoginService;
import org.wecancodeit.pantryplus2electricboogaloo.cart.Cart;
import org.wecancodeit.pantryplus2electricboogaloo.lineitem.LineItem;
import org.wecancodeit.pantryplus2electricboogaloo.lineitem.LineItemRepository;
import org.wecancodeit.pantryplus2electricboogaloo.product.Product;
import org.wecancodeit.pantryplus2electricboogaloo.product.ProductRepository;
import org.wecancodeit.pantryplus2electricboogaloo.user.PantryUser;

public class CartRestController {

	@Resource
	private LoginService loginService;
	
	@Resource
	private ProductRepository productRepo;
	
	@Resource
	private LineItemRepository lineItemRepo;
	
	public void receivePostOnProduct(@AuthenticationPrincipal OAuth2User googleId, long productId) {
		PantryUser user = loginService.resolveUser(googleId);
		Cart cart = user.getCart();
		Product product = productRepo.findById(productId).get();
		LineItem lineItem = new LineItem(cart, product);
		lineItemRepo.save(lineItem);
	}

}
