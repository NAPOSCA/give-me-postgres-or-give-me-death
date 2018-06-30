package org.wecancodeit.pantryplus2electricboogaloo.currency;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Currency {

	@Id
	@GeneratedValue
	private long id;
	private String name;

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

}
