package org.wecancodeit.pantryplus2electricboogaloo.cart;

import static java.util.stream.Collectors.toSet;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.wecancodeit.pantryplus2electricboogaloo.currency.Currency;
import org.wecancodeit.pantryplus2electricboogaloo.lineitem.CountedLineItem;
import org.wecancodeit.pantryplus2electricboogaloo.lineitem.LineItem;
import org.wecancodeit.pantryplus2electricboogaloo.product.PricedProduct;
import org.wecancodeit.pantryplus2electricboogaloo.product.Product;
import org.wecancodeit.pantryplus2electricboogaloo.user.PantryUser;

@Entity
public class Cart {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private long id;
	@OneToOne
	private PantryUser user;
	@OneToMany(mappedBy = "cart", orphanRemoval = true)
	private Set<LineItem> lineItems;

	public Cart() {
	}

	public Cart(PantryUser user) {
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public PantryUser getUser() {
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
		return lineItems.stream().filter(item -> isCountedLineItem(item)).map(item -> (CountedLineItem) item)
				.collect(toSet());
	}

	private boolean isCountedLineItem(LineItem item) {
		return item instanceof CountedLineItem;
	}

	public int amountUsed(Currency currency) {
		return getCountedLineItems().stream().mapToInt(lineItem -> {
			Product product = lineItem.getProduct();
			if (product instanceof PricedProduct) {
				PricedProduct pricedProduct = (PricedProduct) product;
				if (pricedProduct.getCurrency().equals(currency)) {
					return pricedProduct.getPrice() * lineItem.getQuantity();
				}
			}
			return 0;
		}).sum();
	}

	public boolean has(Product product) {
		return getAllLineItems().stream().anyMatch(lineItem -> lineItem.getProduct().equals(product));
	}

	public Optional<LineItem> getLineItemContaining(long productId) {
		return getAllLineItems().stream().filter(lineItem -> lineItem.getProduct().getId() == productId).findFirst();
	}

}
