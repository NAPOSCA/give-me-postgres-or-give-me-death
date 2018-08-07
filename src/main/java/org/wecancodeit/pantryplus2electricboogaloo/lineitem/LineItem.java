package org.wecancodeit.pantryplus2electricboogaloo.lineitem;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.wecancodeit.pantryplus2electricboogaloo.cart.Cart;
import org.wecancodeit.pantryplus2electricboogaloo.product.Product;

@Entity
public class LineItem {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private long id;

	@ManyToOne
	private Cart cart;

	@ManyToOne
	private Product product;

	public LineItem() {
	}

	public LineItem(Cart cart, Product product) {
		this.cart = cart;
		this.product = product;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LineItem other = (LineItem) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public Product getProduct() {
		return product;
	}

}
