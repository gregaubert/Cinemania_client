package com.cinemania.cases;

import org.andengine.entity.sprite.ButtonSprite;
import org.json.JSONException;
import org.json.JSONObject;

import com.cinemania.constants.AllConstants;
import com.cinemania.gamelogic.Player;
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


	@Override
	public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onTheCell(Player player) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public JSONObject toJson() throws JSONException {
		JSONObject jsonCell = new JSONObject();
		
		jsonCell.put("type", HeadQuarters.TYPE);
		jsonCell.put("level", this.getLevel());
		
		return jsonCell;
	}

}
