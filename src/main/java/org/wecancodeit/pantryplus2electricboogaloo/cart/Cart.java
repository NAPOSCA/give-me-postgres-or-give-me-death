package org.wecancodeit.pantryplus2electricboogaloo.cart;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.wecancodeit.pantryplus2electricboogaloo.user.User;

@Entity
public class Cart {

	@Id
	private long id;

	public Cart() {
	}

	public Cart(User user) {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

}
