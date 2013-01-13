package com.cinemania.cells;

import org.andengine.entity.sprite.ButtonSprite;
import org.json.JSONException;
import org.json.JSONObject;

import static com.cinemania.constants.AllConstants.*;

import com.cinemania.activity.R;
import com.cinemania.constants.AllConstants;
import com.cinemania.gamelogic.Player;
import com.cinemania.network.GameContext;
import com.cinemania.resources.ResourcesManager;

public class School extends Resource {
	
	public static final int TYPE = 5;

	private boolean danceExtension =  false;
	private boolean theatreExtension =  false;
	private boolean musicExtension =  false;
	private boolean makeupExtension =  false;
	private boolean disguiseExtension =  false;
	
	
	public School(int level, float posX, float posY) {
		super(ResourcesManager.getInstance().mCaseActors, level, posX, posY);
		setOnClickListener(this);
		setBaseValue(AllConstants.BASEVALUE_OF_SCHOOL);
	}

	@Override
	public int totalValue() {
		if (!this.hasOwner())
			return BASEVALUE_OF_SCHOOL;
		else
			return (int)((BASEVALUE_OF_SCHOOL + getLevel() * PRICE_SCHOOL_EXTENSION) * AllConstants.RATE_SALE);
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
		int profit =(int)((stopTurn-startTurn) * profitRessource(getLevel()) * AllConstants.BASE_SCHOOL_INCOME);
		profit *= FACTOR_DIVIDE_SCHOOL;
		return profit;
	}

	private int nbExtensions() {
		int nb = 0;
		nb = danceExtension ? 1 : 0;
		nb += theatreExtension ? 1 : 0;
		nb += musicExtension ? 1 : 0;
		nb += makeupExtension ? 1 : 0;
		nb += disguiseExtension ? 1 : 0;
		return nb;
	}

	public boolean hasDanceExtension() {
		return danceExtension;
	}

	public void setDanceExtension(boolean danceExtension) {
		this.danceExtension = danceExtension;
	}

	public boolean hasTheatreExtension() {
		return theatreExtension;
	}

	public void setTheatreExtension(boolean theatreExtension) {
		this.theatreExtension = theatreExtension;
	}

	public boolean hasMusicExtension() {
		return musicExtension;
	}

	public void setMusicExtension(boolean musicExtension) {
		this.musicExtension = musicExtension;
	}

	public boolean hasMakeupExtension() {
		return makeupExtension;
	}

	public void setMakeupExtension(boolean makeupExtension) {
		this.makeupExtension = makeupExtension;
	}

	public boolean hasDisguiseExtension() {
		return disguiseExtension;
	}

	public void setDisguiseExtension(boolean disguiseExtension) {
		this.disguiseExtension = disguiseExtension;
	}
	
	@Override
	public JSONObject toJson() throws JSONException {
		JSONObject jsonCell = new JSONObject();
		
		jsonCell.put("type", School.TYPE);
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
		showBuyDialog(player, R.drawable.ic_actors, R.string.title_actors, R.string.txt_acteurs, AllConstants.BASE_SCHOOL_INCOME);
	}

	@Override
	public void ownerOnCell() {
		assert hasOwner();
		int price = (int)(AllConstants.PRICE_SCHOOL_EXTENSION * Math.pow(AllConstants.INFLATION, GameContext.getSharedInstance().getCurrentTurn()));
		showOwnerDialog(R.drawable.ic_actors, R.string.title_actors, getOwner().getLastActors(), price);
	}

	@Override
	public void resetLevel() {
		this.setLevel(0);
	}
}
