package org.wecancodeit.pantryplus2electricboogaloo.currency;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Collection;
import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.wecancodeit.pantryplus2electricboogaloo.product.PricedProduct;
import org.wecancodeit.pantryplus2electricboogaloo.user.PantryUser;

@Entity
public class Currency {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private long id;
	private String name;

	@OneToMany(mappedBy = "currency", orphanRemoval = true)
	private Collection<PricedProduct> pricedProducts;
	private HashMap<Integer, Integer> familySizeToAllowance;

	public Currency() {
	}

	public Currency(String name, HashMap<Integer, Integer> familySizeToAllowance) {
		this.name = name;
		this.familySizeToAllowance = familySizeToAllowance;
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

	public int allowanceOf(PantryUser user) {
		int familySize = user.getFamilySize();
		return allowanceOf(familySize);
	}

	private int allowanceOf(int familySize) {
		if (familySize == 0) {
			return 1;
		}
		if (familySizeToAllowance.containsKey(familySize)) {
			return familySizeToAllowance.get(familySize);
		}
		return allowanceOf(familySize - 1);
	}

}
