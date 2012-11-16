package com.cinemania.client;

import org.andengine.opengl.texture.region.ITextureRegion;

import com.cinemania.ressource.ResourcesManager;

public class Resource extends Case {

	private int level = 0;

	public Resource(ITextureRegion texture) {
		super(texture);
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

	/**
	 * Return the resource according to the kind of building. The compute is the actual depend on
	 * the level and number of extensions. 
	 */
	int profit() {
		return 0;
	}
	
}
