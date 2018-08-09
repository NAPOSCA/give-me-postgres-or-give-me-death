package org.wecancodeit.pantryplus2electricboogaloo.currency;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.stream;
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
	private String unit;

	public Currency() {
	}

	public Currency(String name, String allowanceMap, String unit) {
		this(name);
		this.familySizeToAllowance = new HashMap<>();
		setAllowanceMap(allowanceMap);
		this.unit = unit;
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

	public String getAllowanceMap() {
		return familySizeToAllowance.toString();
	}

	public void setAllowanceMap(String representationMap) {
		familySizeToAllowance.clear();
		stream(representationMap.split(", |,|\\{|\\}")).filter(element -> element.length() > 0).forEach(pair -> {
			int equalsIndex = pair.indexOf("=");
			int key = parseInt(pair.substring(0, equalsIndex));
			int value = parseInt(pair.substring(equalsIndex + 1));
			familySizeToAllowance.put(key, value);
		});
	}

	public String getUnit() {
		return unit;
	}

}
