package org.wecancodeit.pantryplus2electricboogaloo.product;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.wecancodeit.pantryplus2electricboogaloo.category.Category;
import org.wecancodeit.pantryplus2electricboogaloo.currency.Currency;

@Entity
public class PricedProduct extends LimitedProduct {

	private int price;

	@ManyToOne
	private Currency currency;

	public PricedProduct() {
	}

	public PricedProduct(String name, Category category, String imagePath, boolean infantsRequired, int maximumQuantity,
			Currency currency, int price) {
		super(name, category, imagePath, infantsRequired, maximumQuantity);
		this.currency = currency;
		this.price = price;
	}

	public int getPrice() {
		return price;
	}

	public Currency getCurrency() {
		return currency;
	}

	@Override
	public String getType() {
		return "PricedProduct";
	}

}
