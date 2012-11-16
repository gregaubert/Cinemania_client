package com.cinemania.client;

public class Resource extends Case {

	private int level = 0;

	public Resource() {
		super();
	}

	@Override
	public int totalValue() {
		int baseValue = getBaseValue();
		if (this.hasOwner())
			return (int) ((double) baseValue * RATE_SALE);

		return baseValue;
	}

	void increaseLevel() {
		if (level < LEVEL_MAX_BUILDING)
			level++;
	}

	int getLevel() {
		return level;
	}

	int getResource() {
		return 0;
	}

}
