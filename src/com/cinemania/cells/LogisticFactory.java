package com.cinemania.cells;

import org.andengine.entity.sprite.ButtonSprite;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.cinemania.activity.R;
import com.cinemania.constants.AllConstants;
import com.cinemania.gamelogic.Player;
import com.cinemania.network.GameContext;
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
		setOnClickListener(this);
		setBaseValue(AllConstants.BASEVALUE_OF_LOGISTIC);
	}

	@Override
	public int totalValue() {
		if (!this.hasOwner())
			return BASEVALUE_OF_LOGISTIC;
		else
			return (int)((BASEVALUE_OF_LOGISTIC + getLevel() * PRICE_LOGISTIC_EXTENSION) * AllConstants.RATE_SALE);
	}
	
	@Override
	public void upgrade(int price) {
		assert getLevel() < AllConstants.LEVEL_MAX_BUILDING;
		assert getOwner() != null;
		getOwner().looseMoney(price);
		upgradeLevel();
	}

	@Override
	public int profit(int startTurn, int stopTurn){
		int profit =(int)((stopTurn-startTurn) * profitRessource(getLevel()) * AllConstants.BASE_LOGISTIC_INCOME);
		profit *= FACTOR_DIVIDE_FACTORY;
		return profit;
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
	public JSONObject toJson() throws JSONException {
		JSONObject jsonCell = new JSONObject();
		
		jsonCell.put("type", LogisticFactory.TYPE);
		jsonCell.put("level", this.getLevel());
	
		return jsonCell;
	}
	
	@Override
	public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		if(GameContext.getSharedInstance().getPlayer().equals(this.getOwner())){
			ownerOnCell();
		}
	}

	@Override
	public void askToBuy(Player player) {
		showBuyDialog(player, R.drawable.ic_logistics, R.string.title_logistics, R.string.txt_logistique, AllConstants.BASE_LOGISTIC_INCOME);
	}

	@Override
	public void ownerOnCell() {
		assert hasOwner();
		int price = (int)(AllConstants.PRICE_LOGISTIC_EXTENSION * Math.pow(AllConstants.INFLATION, GameContext.getSharedInstance().getCurrentTurn()));
		showOwnerDialog(R.drawable.ic_logistics, R.string.title_logistics, getOwner().getLastLogistics(), price);
	}

	@Override
	public void resetLevel() {
		this.setLevel(0);
	}
}
