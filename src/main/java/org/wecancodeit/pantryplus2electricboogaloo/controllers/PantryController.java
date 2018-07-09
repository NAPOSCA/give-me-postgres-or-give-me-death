package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wecancodeit.pantryplus2electricboogaloo.category.CategoryRepository;

@Controller
public class PantryController {
	
	@Resource
	private CategoryRepository categoryRepo;
	
	@RequestMapping("/")
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
