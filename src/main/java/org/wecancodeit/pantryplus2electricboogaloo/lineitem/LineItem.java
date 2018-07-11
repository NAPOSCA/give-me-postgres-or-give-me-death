package org.wecancodeit.pantryplus2electricboogaloo.lineitem;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.wecancodeit.pantryplus2electricboogaloo.cart.Cart;

@Entity
public class LineItem {

	@Id
	private long id;
	@ManyToOne
	private Cart cart;

}
