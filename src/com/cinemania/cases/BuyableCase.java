package com.cinemania.cases;

import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.cinemania.gamelogic.Player;

public abstract class BuyableCase extends OwnableCell {

	private int baseValue;
	
	public BuyableCase(ITextureRegion texture, float posX, float posY) {
		super(texture, posX, posY);
	}

	/**
	 * Buy the case. The player become the owner and this method decrease the
	 * money of the player.
	 * 
	 * @param p
	 *            : the player
	 */
	public void buy(Player p) {
		p.addProperty(this);
		this.setOwner(p);
		p.setAmount(p.getAmount() - totalValue());
	}

	public abstract int totalValue();

	public int getBaseValue() {
		return baseValue;
	}

	public void setBaseValue(int value) {
		baseValue = value;
	}
}
