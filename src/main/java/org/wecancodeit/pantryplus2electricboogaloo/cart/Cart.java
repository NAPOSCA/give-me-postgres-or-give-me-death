package org.wecancodeit.pantryplus2electricboogaloo.cart;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.wecancodeit.pantryplus2electricboogaloo.lineitem.LineItem;
import org.wecancodeit.pantryplus2electricboogaloo.user.User;

@Entity
public class Cart {

	@Id
	@GeneratedValue
	private long id;
	@OneToOne
	private User user;

	public Cart() {
	}

	public Cart(User user) {
		this.user = user;
	}

	public long getId() {
		return id;
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
		Cart other = (Cart) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public Collection<LineItem> getLineItems() {
		// TODO Auto-generated method stub
		return null;
	}

}
