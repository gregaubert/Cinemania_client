package com.cinemania.cases;

import org.andengine.entity.sprite.ButtonSprite;
import org.json.JSONException;
import org.json.JSONObject;

import com.cinemania.gamelogic.Player;
import com.cinemania.resources.ResourcesManager;
import static com.cinemania.constants.AllConstants.*;

public class LogisticFactory extends Resource {
	
	public static final int TYPE = 6;

	private boolean sceneryExtension =  false;
	private boolean costumeExtension =  false;
	private boolean hairdressingExtension =  false;
	private boolean fxExtension =  false;

	public LogisticFactory(int level, float posX, float posY) {
		super(ResourcesManager.getInstance().mCaseLogistics, level, posX, posY);
	}

	@Override
	public int totalValue() {
		return BASEVALUE_OF_LOGISTIC * getLevel() + nbExtensions() * PRICE_LOGISTIC_EXTENSION;
	}

	@Override
	public int profit(int startTurn, int stopTurn){
		return getLevel() + nbExtensions();
	}

	private int nbExtensions() {
		int nb = 0;
		nb = fxExtension ? 1 : 0;
		nb += sceneryExtension ? 1 : 0;
		nb += costumeExtension ? 1 : 0;
		nb += hairdressingExtension ? 1 : 0;
		return nb;
	}

	public boolean hasFxExtension() {
		return fxExtension;
	}

	public void setFxExtension(boolean fxExtension) {
		this.fxExtension = fxExtension;
	}

	public boolean hasSceneryExtension() {
		return sceneryExtension;
	}

	public void setSceneryExtension(boolean sceneryExtension) {
		this.sceneryExtension = sceneryExtension;
	}

	public boolean hasHairdressingExtension() {
		return hairdressingExtension;
	}

	public void setHairdressingExtension(boolean hairdressingExtension) {
		this.hairdressingExtension = hairdressingExtension;
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
		
		jsonCell.put("type", LogisticFactory.TYPE);
		jsonCell.put("level", this.getLevel());
	
		return jsonCell;
	}
}
