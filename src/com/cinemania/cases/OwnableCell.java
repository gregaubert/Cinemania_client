package com.cinemania.cases;

import org.andengine.opengl.texture.region.ITextureRegion;

import com.cinemania.gamelogic.Player;

public abstract class OwnableCell extends Case {
	
	private Player mOwner;

	
	public OwnableCell(ITextureRegion texture) {
		super(texture);
	}
	
	
	public abstract void upgrade();
	
	public abstract int getLevel();
	
	public abstract boolean updateAvailable();
	
	
	public boolean hasOwner() {
		return mOwner != null;
	}
	
	public void setOwner(Player owner) {
		mOwner = owner;
		setColor(owner.getColorCase());
	}
	
	public Player getOwner() {
		return mOwner;
	}
}
