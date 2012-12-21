package com.cinemania.cases;

import org.andengine.entity.sprite.ButtonSprite;
import org.json.JSONException;
import org.json.JSONObject;

import com.cinemania.activity.R;
import com.cinemania.constants.AllConstants;
import com.cinemania.gamelogic.Player;
import com.cinemania.network.GameContext;
import com.cinemania.resources.ResourcesManager;

public class HeadQuarters extends OwnableCell {
	
	public static final int TYPE = 1;

	private int mLevel;
	
	
	public HeadQuarters(int level, float posX, float posY) {
		super(ResourcesManager.getInstance().mCaseHQ, posX, posY);
		setLevel(level);
		setOnClickListener(this);
	}
	
	
	@Override
	public void upgrade() {
		assert mLevel < AllConstants.LEVEL_MAX_BUILDING;
		mLevel++;
		addLevel(getLevel());
	}
	
	@Override
	public int getLevel() {
		return mLevel;
	}
	
	protected void setLevel(int level){
		mLevel = level;
		addLevel(mLevel);
	}

	@Override
	public boolean updateAvailable() {
		return mLevel < AllConstants.LEVEL_MAX_BUILDING;
	}

	@Override
	public JSONObject toJson() throws JSONException {
		JSONObject jsonCell = new JSONObject();
		
		jsonCell.put("type", HeadQuarters.TYPE);
		jsonCell.put("level", this.getLevel());
		
		return jsonCell;
	}
	
	@Override
	public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		// TODO Virer cette ligne
		onTheCell(GameContext.getSharedInstance().getCurrentPlayer());
		// TODO Afficher la vue du quartier general si c'est le owner qui clique, sinon afficher des infos du owner
	}
	
	@Override
	public void onTheCell(Player player) {
		assert hasOwner();
		if(getOwner().equals(player))
			ownerOnCell();
		else
			strangerOnCell(player);
	}

	@Override
	public void ownerOnCell() {
		// Encaisse le double si on tombe pile dessus
		getOwner().encaisser();
	}

	@Override
	public void strangerOnCell(Player player) {
		showPayDialog(AllConstants.COSTS_ON_HQ, R.drawable.ic_hq, R.string.title_hq);
		player.payOpponent(getOwner(), AllConstants.COSTS_ON_HQ);
	}
}
