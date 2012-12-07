package com.cinemania.cases;

import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import static com.cinemania.constants.AllConstants.*;

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
	}

	@Override
	public int totalValue() {
		return BASEVALUE_OF_SCHOOL * getLevel() + nbExtensions() * PRICE_SCHOOL_EXTENSION;
	}
	
	@Override
	public int profit(int startTurn, int stopTurn){
		return getLevel() + nbExtensions();
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
	public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTheCell() {
		// TODO Auto-generated method stub
		
	}

}
