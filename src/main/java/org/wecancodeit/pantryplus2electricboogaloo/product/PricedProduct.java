package org.wecancodeit.pantryplus2electricboogaloo.product;

import org.wecancodeit.pantryplus2electricboogaloo.category.Category;
import org.wecancodeit.pantryplus2electricboogaloo.currency.Currency;

public class PricedProduct extends LimitedProduct {

	private int cost;

	public PricedProduct(String name, Category category, int maximumQuantity, Currency currency, int cost) {
		super(name, category, maximumQuantity);
		this.cost = cost;
	}

	public int getPrice() {
		return cost;
	}

}
