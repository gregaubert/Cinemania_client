package com.cinemania.cases;

import org.andengine.opengl.texture.region.ITextureRegion;

import com.cinemania.gamelogic.Profitable;
import com.cinemania.resources.ResourcesManager;
import com.cinemania.constants.AllConstants;

public class Resource extends BuyableCase implements Profitable {

	private int mLevel = 0;

	// FIXME: Qu'est-ce que c'est ça
	public Resource() {
		super(ResourcesManager.getInstance().mCaseResource);
	}
	
	
	public Resource(ITextureRegion texture, int level) {
		super(texture);
		mLevel = level;
	}
	
	
	@Override
	public void upgrade() {
		assert mLevel < AllConstants.LEVEL_MAX_BUILDING;
		mLevel++;
	}

	@Override
	public int getLevel() {
		return mLevel;
	}
	
	@Override
	public boolean updateAvailable() {
		return mLevel < AllConstants.LEVEL_MAX_BUILDING;
	}

	
	@Override
	public int totalValue() {
		int baseValue = getBaseValue();
		if (this.hasOwner())
			return (int) ((double) baseValue * AllConstants.RATE_SALE);

		return baseValue;
	}
	
	/**
	 * Return the resource according to the kind of building. The compute is the actual depend on
	 * the level, number of extensions, the start and stop turn.
	 */
	@Override
	public int profit(int startTurn, int stopTurn) {
		return 0;
	}
	
}
