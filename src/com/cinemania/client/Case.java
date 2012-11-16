package com.cinemania.client;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.Texture;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.cinemania.activity.Base;

public abstract class Case implements Constantes {

	private Player owner;

	private int baseValue;
	
	private Sprite sprite;	

	public Case(ITextureRegion texture) {
		sprite = new Sprite(0, 0, texture, Base.getSharedInstance().getVertexBufferObjectManager());
	}
	
	public Sprite getSprite(){
		return sprite;
	}

	/**
	 * Buy the case. The player become the owner and this method decrease the
	 * amount of the player.
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
