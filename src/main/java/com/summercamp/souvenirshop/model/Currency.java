package com.summercamp.souvenirshop.model;

/**
 * Currency with ISO codes
 */
public enum Currency {
	EUR("Euro"),
	USD("United States dollar"),
	UAH("Ukrainian hryvnia"),
	PLN("Polish zloty"),
	BGN("Bulgarian lev"),
	CZK("Czech koruna"),
	MDL("Moldovan leu");

	private final String name;

	public String getName() {
		return name;
	}

	Currency(String name) {
		this.name = name;
	}

	public boolean equalsName(String otherName) {
		return name.equals(otherName);
	}

}
