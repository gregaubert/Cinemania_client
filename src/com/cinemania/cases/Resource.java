package com.cinemania.cases;

import org.andengine.opengl.texture.region.ITextureRegion;

import com.cinemania.gamelogic.Profitable;
import com.cinemania.resources.ResourcesManager;
import com.cinemania.constants.AllConstants;

public abstract class Resource extends BuyableCase implements Profitable {

	private int mLevel;
	private static final int LAYER_LEVEL = 1;
	
	public Resource(float posX, float posY) {
		super(ResourcesManager.getInstance().mCaseResource, posX, posY);
		this.setLevel(0);
	}
	
	public Resource(ITextureRegion texture, int level, float posX, float posY) {
		super(texture, posX, posY);
		this.setLevel(level);
	}
	
	private void setLevel(int level){
		this.mLevel = level;
		//TODO Ajouter le Sprite avec 1,2,3 étoiles.
	}
	
	@Override
	public void upgrade() {
		assert mLevel < AllConstants.LEVEL_MAX_BUILDING;
		this.setLevel(this.getLevel()+1);
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
