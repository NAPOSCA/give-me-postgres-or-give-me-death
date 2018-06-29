package org.wecancodeit.pantryplus2electricboogaloo.product;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.wecancodeit.pantryplus2electricboogaloo.category.Category;

@Entity
public class Product {

	@Id
	private long id;
	private String name;

	public Product(String name, Category category) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
