package org.wecancodeit.pantryplus2electricboogaloo.product;

import javax.persistence.Entity;

import org.wecancodeit.pantryplus2electricboogaloo.category.Category;

@Entity
public class LimitedProduct extends Product {

	private int maximumQuantity;

	public LimitedProduct() {
	}

	public LimitedProduct(String name, Category category, String imagePath, boolean infantsRequired,
			int maximumQuantity) {
		super(name, category, imagePath, infantsRequired);
		this.maximumQuantity = maximumQuantity;
	}

	public int getMaximumQuantity() {
		return maximumQuantity;
	}

	@Override
	public String getType() {
		return "LimitedProduct";
	}

}
