package org.wecancodeit.pantryplus2electricboogaloo.currency;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.wecancodeit.pantryplus2electricboogaloo.product.PricedProduct;

@Entity
public class Currency {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private long id;
	private String name;
	
	@OneToMany(mappedBy = "currency")
	private Collection<PricedProduct> pricedProducts;

	public Currency() {
	}

	public Currency(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public long getId() {
		return id;
	}

	public Collection<PricedProduct> getPricedProducts() {
		return pricedProducts;
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
		Currency other = (Currency) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
