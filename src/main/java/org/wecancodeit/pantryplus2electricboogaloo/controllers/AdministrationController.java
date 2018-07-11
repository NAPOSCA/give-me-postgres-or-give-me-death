package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wecancodeit.pantryplus2electricboogaloo.category.CategoryRepository;
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
}
