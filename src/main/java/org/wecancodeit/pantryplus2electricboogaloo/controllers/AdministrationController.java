package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@RequestMapping(path = "admin/categories", method = GET)
	public String displayAdminCategoriesView(Model model) {
		model.addAttribute("categories", categoryRepo.findAll());
		return "admin/categories";
	}

	@RequestMapping(path = "admin/categories/{categoryId}", method = GET)
	public String displayAdminCategoryView(Model model, @PathVariable Long categoryId) {
		Optional<Category> potentialCategory = categoryRepo.findById(categoryId);
		if (potentialCategory.isPresent()) {
			model.addAttribute("category", potentialCategory.get());
			return "admin/category";
		}
		return "redirect:/admin/categories";
	}

	@RequestMapping(path = "admin/categories/{categoryId}/products/{productId}", method = GET)
	public String displayAdminProductView(Model model, @PathVariable Long categoryId, @PathVariable Long productId) {
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

	@RequestMapping(path = "/admin/categories", method = POST)
	public String receiveAPostRequestOnCategories(@RequestParam String categoryName) {
		categoryRepo.save(new Category(categoryName));
		return "redirect:/admin/categories";
	}

	@RequestMapping(path = "/admin/categories/{categoryId}/products", method = POST)
	public String receiveAPostRequestOnACategorysProducts(Model model, @PathVariable long categoryId,
			@RequestParam String productName, @RequestParam String type, @RequestParam int maximumQuantity,
			@RequestParam int valueInCurrency, @RequestParam String currencyName) {
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

}
