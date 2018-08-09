package org.wecancodeit.pantryplus2electricboogaloo.lineitem;

import javax.persistence.Entity;

import org.wecancodeit.pantryplus2electricboogaloo.cart.Cart;
import org.wecancodeit.pantryplus2electricboogaloo.product.LimitedProduct;

@Entity
public class CountedLineItem extends LineItem {

	private int quantity;

	public CountedLineItem() {
	}

	public CountedLineItem(Cart cart, LimitedProduct product) {
		super(cart, product);
		quantity = 1;
	}

	public void increase() {
		this.quantity += 1;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
