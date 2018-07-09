package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import javax.annotation.Resource;

import org.springframework.ui.Model;
import org.wecancodeit.pantryplus2electricboogaloo.category.CategoryRepository;

public class PantryController {
	
	@Resource
	private CategoryRepository categoryRepo;

	public String displayUserForm() {
		return "user-form";
	}

	public String displayShopping(Model model) {
		model.addAttribute("categories", categoryRepo.findAll());
		return "shopping";
	}

	public String displayCart() {
		return "cart";
	}

}
