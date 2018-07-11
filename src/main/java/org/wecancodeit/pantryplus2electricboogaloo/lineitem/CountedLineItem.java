package org.wecancodeit.pantryplus2electricboogaloo.lineitem;

import javax.persistence.Entity;

import org.wecancodeit.pantryplus2electricboogaloo.cart.Cart;

@Entity
public class CountedLineItem extends LineItem {

	public CountedLineItem() {
	}

	public CountedLineItem(Cart cart) {
		super(cart);
	}

}
