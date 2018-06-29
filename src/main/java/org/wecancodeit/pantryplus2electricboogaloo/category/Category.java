package org.wecancodeit.pantryplus2electricboogaloo.category;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Category {
	
	@Id
	private long id;
	private String name;

	public Category(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
