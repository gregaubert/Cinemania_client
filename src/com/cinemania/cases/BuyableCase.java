package com.cinemania.cases;

import org.andengine.opengl.texture.region.ITextureRegion;

import com.cinemania.gamelogic.Player;

public abstract class BuyableCase extends Case{

	private Player owner;

	private int baseValue;
	
	public BuyableCase(ITextureRegion texture) {
		super(texture);
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

	public void setOwner(Player owner) {
		this.owner = owner;
		setColor(owner.getColorCase());
	}

	public Player getOwner() {
		return owner;
	}

	public abstract int totalValue();

	public boolean hasOwner() {
		return owner != null;
	}

	public int getBaseValue() {
		return baseValue;
	}

	public void setBaseValue(int value) {
		baseValue = value;
	}
}
