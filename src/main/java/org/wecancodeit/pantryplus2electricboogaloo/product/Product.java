package org.wecancodeit.pantryplus2electricboogaloo.product;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.wecancodeit.pantryplus2electricboogaloo.category.Category;
import org.wecancodeit.pantryplus2electricboogaloo.lineitem.LineItem;
import org.wecancodeit.pantryplus2electricboogaloo.user.PantryUser;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private long id;
	private String name;

	@ManyToOne
	private Category category;
	private String image;
	private boolean infantsRequired;
	
	@OneToMany(orphanRemoval = true, cascade = ALL)
	private Collection<LineItem> lineItems;

	public Product() {
	}

	public Product(String name, Category category, String imagePath, boolean infantsRequired) {
		this.name = name;
		this.category = category;
		this.image = imagePath;
		this.infantsRequired = infantsRequired;
	}

	public String getName() {
		return name;
	}

	public long getId() {
		return id;
	}

	public Category getCategory() {
		return category;
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
		Product other = (Product) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public String getImage() {
		return image;
	}

	public String getType() {
		return "Product";
	}

	public boolean isVisibleTo(PantryUser user) {
		return user.getHasInfants() || !getInfantsRequired();
	}

	public boolean getInfantsRequired() {
		return infantsRequired;
	}

}
