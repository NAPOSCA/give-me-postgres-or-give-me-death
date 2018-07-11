package org.wecancodeit.pantryplus2electricboogaloo.lineitem;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.wecancodeit.pantryplus2electricboogaloo.cart.Cart;

@Entity
public class LineItem {

	@Id
	@GeneratedValue
	private long id;
	@ManyToOne
	private Cart cart;

	public LineItem() {
	}

	public LineItem(Cart cart) {
		this.cart = cart;
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

}
