package com.cinemania.cases;

import com.cinemania.constants.AllConstants;
import com.cinemania.resources.ResourcesManager;

public class HeadQuarters extends OwnableCell {
	
	public static final int TYPE = 1;

	private int mLevel = 0;
	
	
	public HeadQuarters(int level, float posX, float posY) {
		super(ResourcesManager.getInstance().mCaseHQ, posX, posY);
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

}
