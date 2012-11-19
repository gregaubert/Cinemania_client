package com.cinemania.cases;

import com.cinemania.constants.Constants;
import com.cinemania.resources.ResourcesManager;

public class HeadQuarters extends Case {

	private int level = 0;
	
	public HeadQuarters(int playerId) {
		super(ResourcesManager.getInstance().mCaseHQ);
		super.setColor(Constants.PLAYER_COLOR[playerId]);
	}

	public int getLevel() {
		return level;
	}
	
	public void increaseLevel() {
		if (level < LEVEL_MAX_BUILDING)
			level++;
	}

}
