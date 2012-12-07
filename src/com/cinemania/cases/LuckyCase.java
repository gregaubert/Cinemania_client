package com.cinemania.cases;

import org.andengine.entity.sprite.ButtonSprite;
import org.json.JSONException;
import org.json.JSONObject;

import com.cinemania.gamelogic.Player;
import com.cinemania.resources.ResourcesManager;

public class LuckyCase extends Cell {
	
	public static final int TYPE = 3;

	public LuckyCase(float posX, float posY) {
		super(ResourcesManager.getInstance().mCaseLuck, posX, posY);
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
		
		jsonCell.put("type", LuckyCase.TYPE);
		
		return jsonCell;
	}
}
