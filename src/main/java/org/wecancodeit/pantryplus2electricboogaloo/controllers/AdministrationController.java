package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.wecancodeit.pantryplus2electricboogaloo.LoginService;
import org.wecancodeit.pantryplus2electricboogaloo.category.Category;
import org.wecancodeit.pantryplus2electricboogaloo.category.CategoryRepository;
import org.wecancodeit.pantryplus2electricboogaloo.currency.Currency;
import org.wecancodeit.pantryplus2electricboogaloo.currency.CurrencyRepository;
import org.wecancodeit.pantryplus2electricboogaloo.product.LimitedProduct;
import org.wecancodeit.pantryplus2electricboogaloo.product.PricedProduct;
import org.wecancodeit.pantryplus2electricboogaloo.product.Product;
import org.wecancodeit.pantryplus2electricboogaloo.product.ProductRepository;

@Controller
public class AdministrationController {

	@Resource
	private CategoryRepository categoryRepo;

	@Resource
	private ProductRepository productRepo;

	@Resource
	private CurrencyRepository currencyRepo;

	@Resource
	private LoginService loginService;

	@GetMapping(path = "admin/categories")
	public String displayAdminCategoriesView(@AuthenticationPrincipal OAuth2User googleId, Model model) {
		checkClearance(googleId);
		model.addAttribute("categories", categoryRepo.findAll());
		return "admin/categories";
	}

	@GetMapping(path = "admin/categories/{categoryId}")
	public String displayAdminCategoryView(OAuth2User googleId, Model model, @PathVariable Long categoryId) {
		checkClearance(googleId);
		Optional<Category> potentialCategory = categoryRepo.findById(categoryId);
		if (potentialCategory.isPresent()) {
			model.addAttribute("category", potentialCategory.get());
			return "admin/category";
		}
		return "redirect:/admin/categories";
	}

	@GetMapping("admin/categories/{categoryId}/products/{productId}")
	public String displayAdminProductView(OAuth2User googleId, Model model, @PathVariable Long categoryId,
			@PathVariable Long productId) {
		checkClearance(googleId);
		model.addAttribute("category", categoryRepo.findById(categoryId));
		Optional<Product> potentialProduct = productRepo.findById(productId);
		model.addAttribute("product", potentialProduct);
		if (!potentialProduct.isPresent()) {
			return "redirect:/admin/categories/" + categoryId;
		}
		if (potentialProduct.get() instanceof PricedProduct) {
			return "admin/priced-product";
		}
		if (potentialProduct.get() instanceof LimitedProduct) {
			return "admin/limited-product";
		}
		return "admin/product";
	}

	@PostMapping("/admin/categories")
	public String receiveAPostRequestOnCategories(OAuth2User googleId, @RequestParam String categoryName) {
		checkClearance(googleId);
		categoryRepo.save(new Category(categoryName));
		return "redirect:/admin/categories";
	}

	@PostMapping("/admin/categories/{categoryId}/products")
	public String receiveAPostRequestOnACategorysProducts(OAuth2User googleId, Model model,
			@PathVariable long categoryId, @RequestParam String productName, @RequestParam String type,
			@RequestParam int maximumQuantity, @RequestParam int valueInCurrency, @RequestParam String currencyName) {
		checkClearance(googleId);
		Optional<Category> potentialCategory = categoryRepo.findById(categoryId);
		if (!potentialCategory.isPresent()) {
			return "redirect:/admin/categories";
		}
		Category category = potentialCategory.get();
		if (type.equals("Product")) {
			Product product = new Product(productName, category);
			productRepo.save(product);
		} else if (type.equals("LimitedProduct")) {
			LimitedProduct product = new LimitedProduct(productName, category, maximumQuantity);
			productRepo.save(product);
		} else if (type.equals("PricedProduct")) {
			Optional<Currency> potentialCurrency = currencyRepo.findByName(currencyName);
			if (potentialCurrency.isPresent()) {
				Currency currency = potentialCurrency.get();
				PricedProduct product = new PricedProduct(productName, category, maximumQuantity, currency,
						valueInCurrency);
				productRepo.save(product);
			} else {
				return "redirect:/admin/currencies";
			}
		}
		return "redirect:/admin/categories/" + categoryId;
	}

	@GetMapping("/admin")
	public String displayAdminView(@AuthenticationPrincipal OAuth2User googleId) {
		checkClearance(googleId);
		return "admin/index";
	}

	private void checkClearance(OAuth2User googleId) {
		if (!loginService.isAdmin(googleId)) {
			throw new AccessDeniedException("403 returned");
		}
	}

}
