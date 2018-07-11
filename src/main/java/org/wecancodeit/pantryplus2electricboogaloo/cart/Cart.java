package org.wecancodeit.pantryplus2electricboogaloo.cart;

import static java.util.stream.Collectors.toSet;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.wecancodeit.pantryplus2electricboogaloo.lineitem.CountedLineItem;
import org.wecancodeit.pantryplus2electricboogaloo.lineitem.LineItem;
import org.wecancodeit.pantryplus2electricboogaloo.user.User;

@Entity
public class Cart {

	@Id
	@GeneratedValue
	private long id;
	@OneToOne
	private User user;
	@OneToMany(mappedBy = "cart", orphanRemoval = true)
	private Set<LineItem> lineItems;

	public Cart() {
	}

	public Cart(User user) {
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public User getUser() {
		return user;
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

	public Map<String, Object> toModel() {
		Map<String, Object> model = new HashMap<>();
		Map<String, Object> user = getUser().toModel();
		model.put("user", user);
		model.put("lineItems", getLineItems());
		model.put("countedLineItems", getCountedLineItems());
		
		
		return model;
	}

	public Set<LineItem> getAllLineItems() {
		return lineItems;
	}

	public Set<LineItem> getLineItems() {
		return lineItems.stream().filter(item -> !isCountedLineItem(item)).collect(toSet());
	}

	public Set<CountedLineItem> getCountedLineItems() {
		return lineItems.stream().filter(item -> isCountedLineItem(item)).map(item -> (CountedLineItem) item).collect(toSet());
	}

	private boolean isCountedLineItem(LineItem item) {
		return item instanceof CountedLineItem;
	}

}
