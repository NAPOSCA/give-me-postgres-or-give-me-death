package org.wecancodeit.pantryplus2electricboogaloo.category;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.wecancodeit.pantryplus2electricboogaloo.product.Product;

@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private long id;
	private String name;

	@OneToMany(mappedBy = "category")
	Collection<Product> products;

	public Category() {
	}

	public Category(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public long getId() {
		return id;
	}

	public Collection<Product> getProducts() {
		return products;
	}

}
