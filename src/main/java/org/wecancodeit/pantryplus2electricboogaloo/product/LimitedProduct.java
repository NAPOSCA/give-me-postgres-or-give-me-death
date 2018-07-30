package org.wecancodeit.pantryplus2electricboogaloo.product;

import org.wecancodeit.pantryplus2electricboogaloo.category.Category;

public class LimitedProduct extends Product {

	private int maximumQuantity;

	public LimitedProduct() {
	}

	public LimitedProduct(String name, Category category, int maximumQuantity, String imagePath) {
		super(name, category, imagePath);
		this.maximumQuantity = maximumQuantity;
	}

	public int getMaximumQuantity() {
		return maximumQuantity;
	}

}
