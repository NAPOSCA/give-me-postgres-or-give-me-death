package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Optional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.wecancodeit.pantryplus2electricboogaloo.category.Category;
import org.wecancodeit.pantryplus2electricboogaloo.category.CategoryRepository;
import org.wecancodeit.pantryplus2electricboogaloo.currency.Currency;
import org.wecancodeit.pantryplus2electricboogaloo.product.LimitedProduct;
import org.wecancodeit.pantryplus2electricboogaloo.product.PricedProduct;
import org.wecancodeit.pantryplus2electricboogaloo.product.Product;
import org.wecancodeit.pantryplus2electricboogaloo.product.ProductRepository;

@Controller
public class AdministrationController {
	
	@Resource
	private CategoryRepository categoryRepo;
	
	@Resource
	private EntityManager entityManager;
	
	@Resource
	private ProductRepository productRepo;

	@RequestMapping(value="admin/categories", method = GET)
	public String displayAdminCategoriesView(Model model) {
		model.addAttribute("categories", categoryRepo.findAll());
		return "admin/categories";
	}

	@RequestMapping(value = "admin/categories/{categoryId}", method = GET)
	public String displayAdminCategoryView(Model model, @PathVariable Long categoryId) {
		model.addAttribute("category", categoryRepo.findById(categoryId).orElse(null));
		return "admin/category";
	}

	@RequestMapping(value = "admin/categories/{categoryId}/products/{productId}", method = GET)
	public String displayAdminProductView(Model model, @PathVariable Long categoryId, @PathVariable Long productId) {
		model.addAttribute("category", categoryRepo.findById(categoryId));
		Optional<Product> potentialProduct = productRepo.findById(productId);
		model.addAttribute("product", potentialProduct);
		if (potentialProduct.get() instanceof PricedProduct) {
			return "admin/priced-product";
		}
		if (potentialProduct.get() instanceof LimitedProduct) {
			return "admin/limited-product";
		}
		return "admin/product";
	}
	
	@RequestMapping(value = "/admin/categories", method = POST)
	public String receiveAPostRequestOnCategories(@RequestParam String categoryName) {
		categoryRepo.save(new Category(categoryName));
		return "redirect:/admin/categories";
	}

	@Transactional
	@RequestMapping(value = "/admin/categories/{categoryId}/products", method = POST)
	public String receiveAPostRequestOnACategorysProducts(Model model, @PathVariable Long categoryId,
			@RequestParam String type, @RequestParam String productName,
			@RequestParam(defaultValue = "0") int maximumQuantity, @RequestParam(defaultValue = "0") int cost, @RequestParam(defaultValue = "0") Currency currency) {
		Category category = categoryRepo.findById(categoryId).get();
		if (type.equals("Product")) {
			Product product = new Product(productName, category);
			productRepo.save(product);
		} else if (type.equals("LimitedProduct")) {
			LimitedProduct product = new LimitedProduct(productName, category, maximumQuantity);
			productRepo.save(product);
		} else if (type.equals("PricedProduct")) {
			PricedProduct product = new PricedProduct(productName, category, maximumQuantity, currency, cost);
			productRepo.save(product);
		}
		entityManager.flush();
		entityManager.clear();
		category = categoryRepo.findById(categoryId).orElse(null);
		model.addAttribute("category", category);
		return "redirect:/admin/categories/" + categoryId;
	}
}
