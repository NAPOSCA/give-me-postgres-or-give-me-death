package org.wecancodeit.pantryplus2electricboogaloo.product;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.wecancodeit.pantryplus2electricboogaloo.category.Category;
import org.wecancodeit.pantryplus2electricboogaloo.currency.Currency;

@Entity
public class PricedProduct extends LimitedProduct {

	private int cost;
	
	@ManyToOne
	private Currency currency;

	public PricedProduct() {
	}

	public PricedProduct(String name, Category category, int maximumQuantity, Currency currency, int cost) {
		super(name, category, maximumQuantity);
		this.currency = currency;
		this.cost = cost;
	}

	public int getPrice() {
		return cost;
	}

	public Currency getCurrency() {
		return currency;
	}

}
