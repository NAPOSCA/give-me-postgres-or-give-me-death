package org.wecancodeit.pantryplus2electricboogaloo.category;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.wecancodeit.pantryplus2electricboogaloo.product.Product;
import org.wecancodeit.pantryplus2electricboogaloo.user.PantryUser;

@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private long id;
	private String name;

	@OneToMany(mappedBy = "category", orphanRemoval = true)
	Collection<Product> products;
	private boolean schoolAgeChildrenRequired;

	public Category() {
	}

	public Category(String name, boolean schoolAgeChildrenRequired) {
		this.name = name;
		this.schoolAgeChildrenRequired = schoolAgeChildrenRequired;
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

	public int numberOfProducts() {
		return getProducts().size();
	}

	public void updateName(String name) {
		this.name = name;
	}

	public boolean isVisibleTo(PantryUser user) {
		return user.getSchoolAgeChildren() > 0 || !schoolAgeChildrenRequired;
	}
	
	public boolean getSchoolAgeChildrenRequired() {
		return schoolAgeChildrenRequired;
	}

	public void updateSchoolAgeChildrenRequired(boolean schoolAgeChildrenRequired) {
		this.schoolAgeChildrenRequired = schoolAgeChildrenRequired;
	}

}
