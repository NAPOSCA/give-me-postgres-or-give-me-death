package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wecancodeit.pantryplus2electricboogaloo.category.CategoryRepository;

@Controller
public class AdministrationController {
	
	@Resource
	private CategoryRepository categoryRepo;

	@RequestMapping(value="admin/categories", method = GET)
	public String displayAdminCategoriesView(Model model) {
		model.addAttribute("categories", categoryRepo.findAll());
		return "admin/categories";
	}
}
